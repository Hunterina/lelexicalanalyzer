package ru.scratty.ui

import ru.scratty.ast.Statement
import ru.scratty.exception.ParserException
import ru.scratty.lib.Variables
import ru.scratty.parser.Lexer
import ru.scratty.parser.Parser
import ru.scratty.util.toEightBase
import tornadofx.Controller

class EditorController : Controller() {

    private val view: EditorView by inject()

    fun process(text: String) {
        try {
            val tokens = Lexer(text).tokenize()
            tokens.forEach(::println)
            Parser(tokens).parse().forEach(Statement::execute)

            val result = Variables.getAll().joinToString("\n") { "${it.first} = ${it.second.toEightBase()}" }
            view.setResult(result)
        } catch (ex: ParserException) {
            view.setError(ex.msg, ex.pos, ex.len)
        }
    }
}