package ru.scratty.ui

import javafx.scene.control.TextArea
import tornadofx.*

class EditorView : View("Редактор") {

    private val controller: EditorController by inject()

    private var editor: TextArea by singleAssign()
    private var result: TextArea by singleAssign()

    override val root = vbox {
        minWidth = 600.0
        minHeight = 400.0

        editor = textarea {
            style = "-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick; -fx-font-size: 20px;"
            vboxConstraints {
                marginTop = 5.0
                marginLeftRight(5.0)
            }
        }

        hbox {
            vboxConstraints {
                marginTop = 5.0
                marginLeftRight(5.0)
            }
            button("Запустить") {
                hboxConstraints {
                    marginRight = 20.0
                }
                action {
                    controller.process(editor.text)
                }
            }
            button("БНФ") {
                action {
                    openInternalWindow(BNFView::class)
                }
            }
        }

        result = textarea {
            isEditable = false
            vboxConstraints {
                marginTopBottom(5.0)
                marginLeftRight(5.0)
            }
        }
    }

    fun setError(error: String, pos: Int, len: Int) {
        result.text = error
        editor.selectRange(pos - len, pos)
    }

    fun setResult(text: String) {
        result.text = text
    }
}