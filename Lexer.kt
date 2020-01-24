package ru.scratty.parser

import ru.scratty.exception.ParserException
import java.lang.StringBuilder

class Lexer(private val input: String) {

    companion object {

        private val SPACES = " \n\t\r"
        private val LETTERS = "abcdefghijklmnopqrstuvwxyz"
        private val DIGITS = "01234567"

        private val SYMBOLS = mapOf(
            ':' to TokenType.COLON,
            ';' to TokenType.SEMICOLON
        )

        private val WORDS = mapOf(
            "begin" to TokenType.BEGIN,
            "end" to TokenType.END,
            "real" to TokenType.REAL,
            "integer" to TokenType.INTEGER
        )

        private val OPERATORS = mapOf(
            '+' to TokenType.PLUS,
            '-' to TokenType.MINUS,
            '*' to TokenType.STAR,
            '/' to TokenType.SLASH,
            '(' to TokenType.LPAREN,
            ')' to TokenType.RPAREN,
            '=' to TokenType.EQ
        )
    }

    private val length = input.length

    private val tokens: MutableList<Token> = ArrayList()

    private var pos: Int = 0

    fun tokenize(): List<Token> {
        while (pos < length) {
            val current = get()

            when {
                DIGITS.indexOf(current) != -1 -> tokenizeNumber()
                OPERATORS.containsKey(current) -> tokenizeOperator()
                SYMBOLS.containsKey(current) -> tokenizeSymbol()
                LETTERS.indexOf(current.toLowerCase()) != -1 -> tokenizeWord()
                SPACES.indexOf(current) != -1 -> next()
                else -> throw ParserException("Неизвестный символ '$current'", pos + 1, 1)
            }
        }
        return tokens
    }

    private fun tokenizeNumber() {
        val buffer = StringBuilder()

        var current = get()
        while (DIGITS.indexOf(current) != -1) {
            buffer.append(current)
            current = next()
        }
        addToken(TokenType.NUMBER, buffer.toString())
    }

    private fun tokenizeOperator() {
        val tokenType = OPERATORS[get()] ?: throw Exception("Ожидался один из операторов '+-*/'")
        addToken(tokenType, get().toString())
        next()
    }

    private fun tokenizeWord() {
        val buffer = StringBuilder()

        var current = get()
        while (current.isLetterOrDigit()) {
            buffer.append(current)
            current = next()
        }

        val word = buffer.toString()
        if (WORDS.containsKey(word.toLowerCase())) {
            val tokenType = WORDS[word.toLowerCase()] ?: throw Exception("Ожидалось одно из слов 'Begin|End|Real|Integer'")
            addToken(tokenType, word)
        } else {
            addToken(TokenType.WORD, word)
        }
    }

    private fun tokenizeSymbol() {
        val tokenType = SYMBOLS[get()] ?: throw Exception("Ожидался один из символов ':;")
        addToken(tokenType, get().toString())
        next()
    }

    private fun next(): Char {
        pos++
        return get()
    }

    private fun get(relativePosition: Int = 0): Char {
        val position = pos + relativePosition

        return if (position >= length) {
            '\\'
        } else {
            input[position]
        }
    }

    private fun addToken(tokenType: TokenType, text: String) {
        tokens.add(Token(tokenType, text, pos))
    }
}