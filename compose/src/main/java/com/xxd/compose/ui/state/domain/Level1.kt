package com.xxd.compose.ui.state.domain

data class Level1(
    val name: String,
    val age: Int,
    var level2: Level2,
    val level2Object: Level2Object,
)
