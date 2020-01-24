package ru.scratty

import ru.scratty.ui.EditorView
import tornadofx.App

class UILauncher : App(EditorView::class) {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(UILauncher::class.java, *args)
        }
    }
}