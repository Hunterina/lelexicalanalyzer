package ru.scratty.parser

data class Token(
    val type: TokenType,
    val text: String,
    val pos: Int
)