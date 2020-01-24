package ru.scratty.ast

import ru.scratty.util.fromEightBase

class NumberExpression(
    private val value: String
) : Expression {

    override fun eval(): Int = value.fromEightBase()

    override fun toString(): String = value
}