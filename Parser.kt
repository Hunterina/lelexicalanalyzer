package ru.scratty.parser

import ru.scratty.ast.*
import ru.scratty.exception.ParserException

class Parser(private val tokens: List<Token>) {

    companion object {
        private val EOF = Token(TokenType.EOF, "", -1)
    }

    private val size: Int = tokens.size

    private var pos: Int = 0

    fun parse(): List<Statement> = begin()

    private fun begin(): List<Statement> {
        val result: List<Statement>

        val current = get()
        if (match(TokenType.BEGIN)) {
            terms()
            result = statements()
        } else {
            throw ParserException("Ожидалось слово 'Begin' вместо '${current.text}'", current)
        }
        if (!match(TokenType.END)) {
            throw ParserException("Ожидалость слово 'End' вместо '${get().text}'", get())
        }

        return result
    }

    private fun terms() {
        val current = get(0)
        if (current.type != TokenType.REAL && current.type != TokenType.INTEGER) {
            throw ParserException("Ожидалось слово 'Real' или 'Integer' вместо '${current.text}'", current)
        }

        while (true) {
            when {
                match(TokenType.REAL) -> words()
                match(TokenType.INTEGER) -> numbers()
                else -> return
            }
        }
    }

    private fun words() {
        if (!match(TokenType.WORD)) {
            throw ParserException("Ожидалась переменная вместо '${get().text}'", get())
        }

        while (match(TokenType.WORD)) {
            if (get(0).type == TokenType.NUMBER && get(1).type == TokenType.COLON) {
                return
            }
        }
    }

    private fun numbers() {
        if (get().type == TokenType.COLON) {
            throw ParserException("После 'Integer' ожидалось число вместо '${get().text}'", get())
        }
        if (get().type == TokenType.NUMBER && get(1).type == TokenType.COLON) {
            throw ParserException("Перед двоеточием должна быть метка", get())
        }
        if (get().type != TokenType.NUMBER) {
            throw ParserException("После 'Integer' ожидалось число вместо '${get().text}'", get())
        }

        while (match(TokenType.NUMBER)) {
            if (get(0).type == TokenType.NUMBER && get(1).type == TokenType.COLON) {
                return
            }
        }
    }

    private fun statements(): List<Statement> = ArrayList<Statement>().apply {
        if (this@Parser.get().type == TokenType.END) {
            throw ParserException("Ожидалась метка вместо '${this@Parser.get().text}'", this@Parser.get())
        }
        while (this@Parser.get(0).type == TokenType.NUMBER) {
            add(statement())
        }
    }

    private fun statement(): Statement {
        if (!match(TokenType.NUMBER)) {
            throw ParserException("Ожидалась метка вместо '${get().text}'", get())
        }
        if (!match(TokenType.COLON)) {
            throw ParserException("Ожидалось двоеточие вместо '${get().text}'", get())
        }

        val result =  assignmentStatement()

        if (!match(TokenType.SEMICOLON)) {
            throw ParserException("После объявления переменной ожидалась точка с запятой вместо ${get().text}", get())
        }

        return result
    }

    private fun assignmentStatement(): Statement {
        val current = get()
        if (!match(TokenType.WORD)) {
            throw ParserException("Ожидалось название переменной вместо '${current.text}'", current)
        }
        val variable = current.text
        if (!match(TokenType.EQ)) {
            throw ParserException("Ожидалось '=' после названия переменной вместо '${get(1).text}'", get(1))
        }
        return AssignmentStatement(variable, expression())
    }

    private fun expression(): Expression = additive()

    private fun additive(): Expression {
        var result = multiplicative()
        cycle@while (true) {
            result = when {
                match(TokenType.PLUS) -> BinaryExpression('+', result, multiplicative())
                match(TokenType.MINUS) -> BinaryExpression('-', result, multiplicative())
                else -> break@cycle
            }
        }
        return result
    }

    private fun multiplicative(): Expression {
        var result = unary()
        cycle@while (true) {
            result = when {
                match(TokenType.STAR) -> BinaryExpression('*', result, unary())
                match(TokenType.SLASH) -> BinaryExpression('/', result, unary())
                else -> break@cycle
            }
        }
        return result
    }

    private fun unary(): Expression = when {
        match(TokenType.MINUS) -> UnaryExpression('-', primary())
        match(TokenType.PLUS) -> UnaryExpression('+', primary())
        else -> primary()
    }

    private fun primary(): Expression {
        val current = get()
        return when {
            match(TokenType.NUMBER) -> NumberExpression(current.text)
            match(TokenType.LPAREN) -> expression().also {
                if (!match(TokenType.RPAREN)) {
                    throw ParserException("Ожидалась закрывающая скобка вместо '${get().text}'", get())
                }
            }
            match(TokenType.WORD) -> VariableExpression(current.text)
            else -> throw ParserException("Ожидалась правая часть вместо '${current.text}'", current)
        }
    }

    private fun match(tokenType: TokenType): Boolean = if (get().type == tokenType) {
        pos++
        true
    } else {
        false
    }

    private fun get(relativePosition: Int = 0): Token {
        val position = pos + relativePosition
        return if (position >= size) {
            EOF
        } else {
            tokens[position]
        }
    }
}