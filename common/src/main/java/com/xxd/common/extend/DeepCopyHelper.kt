/*
 * Copyright 2021-2021 Sergey Volkov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xxd.common.extend

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.JsonNodeType
import com.fasterxml.jackson.databind.node.ObjectNode
import com.xxd.common.extend.ArrayModificationMode.*

/** Exposed so you can configure it */
val mapper: ObjectMapper = ObjectMapper().findAndRegisterModules()
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
    .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
    .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)

/**
 * Enables easy copying object tree with deeply nested properties.
 * You can use this method on data classes as well as collections.
 * If you mess up with [propertyPath]/[newValue], there are a couple of exceptions to show what's wrong.
 *
 * @param propertyPath property pointers separated by '/'. Use number as pointer to array element. Examples:
 * order/creator/username; lines/0/lineId; 5/products
 * @param newValue object of the same type as what is currently sitting at propertyPath
 * @param arrayModificationMode if your last property pointer is array index, you can replace, add/insert or remove element at this index
 *
 * @throws IllegalArgumentException
 * @throws IllegalStateException
 * @throws InvalidFormatException
 * @throws UnrecognizedPropertyException
 * @throws IndexOutOfBoundsException
 *
 * @author Sergey Volkov
 *
 * 必须要有默认构造函数才能实现，放弃该方法
 *
 * 这里使用的jackson的解析，需要引入2个jackson的包
 * 内部使用是序列化+反序列化的操作来实现深拷贝，而且不需要实现Serializable,Parcelable接口，非常方便
 * 解析加入了缓存配置，初次反序列化时间比较长，需要300-500mm，之后再次反序列化都是100mm以内，10000个数据的集合反序列化页只花了44mm,100个数据的集合反序列化1mm
 * 原来内实现了拷贝时替换某些字段，而且必须替换，这里增加了原始拷贝，不做替换操作
 * github地址:https://github.com/sergey-volkov-lm/kotlin-deep-copy-helper
 * 需要引入的2个jackson包：
 * implementation 'com.fasterxml.jackson.core:jackson-core:2.9.0'
 * implementation 'com.fasterxml.jackson.core:jackson-databind:2.9.0'
 */
inline fun <reified T : Any> T.deepCopy(
    propertyPath: String? = null,
    newValue: Any? = null,
    arrayModificationMode: ArrayModificationMode = REPLACE
): T = deepCopy(propertyPath, newValue, arrayModificationMode, object : TypeReference<T>() {})

/**
 * You are not supposed to use this directly, but it reduces amount of inlined code
 */
fun <T : Any> T.deepCopy(
    propertyPath: String?,
    newValue: Any?,
    arrayModificationMode: ArrayModificationMode,
    type: TypeReference<T>
): T {

    // 这里为修改原来的内容，不需要替换下，不需要做匹配替换操作，直接序列化，反序列化即可
    if (propertyPath == null || propertyPath.isEmpty()) {
        val sourceJsonNode = mapper.valueToTree<JsonNode>(this)
        val resultJson = mapper.writeValueAsString(sourceJsonNode)
        return mapper.readValue(resultJson, type)
    }

    val pathTokensRaw: List<String> = propertyPath.split('/')
    require(!pathTokensRaw.contains("")) {
        "propertyPath must not contain empty parts"
    }
    val onlyDigitsRegex = """\d+""".toRegex()
    val wordRegex = """\w+""".toRegex()
    val pathTokens: List<PathToken> = pathTokensRaw.map {
        PathToken(
            stringValue = it,
            type = when {
                it.matches(onlyDigitsRegex) -> {
                    PathTokenType.ARRAY_INDEX
                }
                it.matches(wordRegex) -> {
                    PathTokenType.PROPERTY
                }
                else -> {
                    throw IllegalArgumentException("propertyPath must contain only [A-Za-z0-9] chars")
                }
            },
        )
    }

    val sourceJsonNode = mapper.valueToTree<JsonNode>(this)
    val newValueJsonNode = mapper.valueToTree<JsonNode>(newValue)

    var parentNode: JsonNode = sourceJsonNode
    pathTokens.dropLast(1).forEach {
        parentNode = if (it.type == PathTokenType.ARRAY_INDEX) {
            parentNode.get(it.intValue) ?: error("Bad index in propertyPath")
        } else { // property
            parentNode.get(it.stringValue) ?: error("Bad property in propertyPath")
        }
    }

    val lastPathToken = pathTokens.last()
    when (parentNode.nodeType) {
        JsonNodeType.ARRAY -> {
            check(lastPathToken.type == PathTokenType.ARRAY_INDEX) {
                "Bad propertyPath. Expected array index at the end."
            }
            val parentArrayNode = parentNode as ArrayNode
            val index = lastPathToken.intValue
            if (index > parentArrayNode.size()) {
                throw IndexOutOfBoundsException("Can't set/add/insert element at index $index. Check propertyPath.")
            }
            when (arrayModificationMode) {
                REPLACE -> {
                    parentArrayNode.set(index, newValueJsonNode)
                }
                INSERT_APPEND -> {
                    parentArrayNode.insert(index, newValueJsonNode)
                }
                REMOVE -> {
                    parentArrayNode.remove(index)
                }
            }
        }
        JsonNodeType.OBJECT -> {
            check(lastPathToken.type == PathTokenType.PROPERTY) {
                "Bad propertyPath. Expected property name at the end."
            }
            (parentNode as ObjectNode).set(lastPathToken.stringValue, newValueJsonNode)
        }
        else -> error("Unexpected parent JsonNode type: ${parentNode.nodeType}, raw value: $parentNode")
    }

    val resultJson = mapper.writeValueAsString(sourceJsonNode)
    return mapper.readValue(resultJson, type)
}

data class PathToken(
    val type: PathTokenType,
    val stringValue: String
) {
    val intValue: Int
        get() = if (type == PathTokenType.ARRAY_INDEX)
            stringValue.toInt()
        else
            error("PathToken $stringValue is property, not array! Check propertyPath.")
}

enum class PathTokenType {
    /** Property name, to be specific */
    PROPERTY,

    /** Integer, starting from 0 */
    ARRAY_INDEX
}

enum class ArrayModificationMode {
    REPLACE,
    INSERT_APPEND,
    REMOVE
}
