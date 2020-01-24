package ru.scratty.ast

import ru.scratty.lib.Variables
import java.lang.Exception

class VariableExpression(
    private val name: String
) : Expression {

    override fun eval(): Int = if (Variables.isExists(name)) {
        Variables.get(name)
    } else {
        throw Exception("Переменная '$name' не найдена")
    }

    override fun toString(): String = "$name [$Variables.get(name)]"
}