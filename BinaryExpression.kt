package ru.scratty.ast

class BinaryExpression(
    private val operation: Char,
    private val expr1: Expression,
    private val expr2: Expression
) : Expression {

    override fun eval(): Int = when(operation) {
        '+' -> expr1.eval() + expr2.eval()
        '-' -> expr1.eval() - expr2.eval()
        '*' -> expr1.eval() * expr2.eval()
        '/' -> expr1.eval() / expr2.eval()
        else -> throw Exception("Некорректная операция, ожидалось '+-*/'")
    }

    override fun toString(): String = "[$expr1 $operation $expr2]"
}