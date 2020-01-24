package ru.scratty.util

fun String.fromEightBase(): Int = toInt(8)

fun Int.toEightBase(): String = toString(8)