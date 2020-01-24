package ru.scratty.ast

class UnaryExpression(
    private val operation: Char,
    private val expr: Expression
) : Expression {

    override fun eval(): Int = when(operation) {
        '+' -> expr.eval()
        '-' -> -expr.eval()
        else -> throw Exception("Некорректная операция, ожидалось '+-'")
    }

    override fun toString(): String = "$operation $expr"


}