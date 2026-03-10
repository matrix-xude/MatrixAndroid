package com.xxd.kt.basic.clazz.sealed

import java.io.File
import javax.sql.DataSource

/**
 *    author : xxd
 *    date   : 2026/3/10
 *    desc   : 
 */

// Create a sealed interface
sealed interface Error

// Create a sealed class that implements sealed interface Error
sealed class IOError(): Error

// Define subclasses that extend sealed class 'IOError'
class FileReadError(val file: File): IOError()
class DatabaseError(val source: DataSource): IOError()

// Create a singleton object implementing the 'Error' sealed interface
object RuntimeError : Error