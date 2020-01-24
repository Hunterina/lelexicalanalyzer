package ru.scratty.exception

import ru.scratty.parser.Token

class ParserException(val msg: String, val pos: Int, val len: Int) : Exception(msg) {
    constructor(msg: String, token: Token) : this(msg, token.pos, token.text.length)
}