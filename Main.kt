package ru.scratty

import ru.scratty.ast.Statement
import ru.scratty.exception.ParserException
import ru.scratty.lib.Variables
import ru.scratty.parser.Lexer
import ru.scratty.parser.Parser

fun main() {
    val input =
"""begin real vv aa integer 12 2 : abc = 2 * (3 + 7); 12 : aaa = 3 * abc; end"""
    val tokens = Lexer(input).tokenize()
    tokens.forEach(::println)

    try {
        val statements = Parser(tokens).parse()
        statements.forEach(Statement::execute)
    } catch (ex: ParserException) {
        println("${ex.message}")
        println("Место ошибки: ${ex.pos}")
    }

    Variables.printAll()
}