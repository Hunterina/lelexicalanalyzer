package ru.scratty.ast

import ru.scratty.lib.Variables

class AssignmentStatement(
    private val variable: String,
    private val expression: Expression
) : Statement {

    override fun execute() {
        Variables.set(variable, expression.eval())
    }

    override fun toString(): String = "$variable = $expression"
}