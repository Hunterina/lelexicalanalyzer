package ru.scratty.lib

object Variables {

    private val variables = HashMap<String, Int>()

    fun isExists(name: String): Boolean = variables.containsKey(name)

    fun get(name: String): Int = variables.getOrDefault(name, 0)

    fun set(name: String, value: Int) {
        variables[name] = value
    }

    fun getAll(): List<Pair<String, Int>> = variables.entries.map {
        it.toPair()
    }

    fun printAll() {
        variables.entries.forEach {
            println("${it.key} = ${it.value}")
        }
    }
}