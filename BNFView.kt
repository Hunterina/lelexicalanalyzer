package ru.scratty.ui

import tornadofx.View
import tornadofx.textarea
import tornadofx.vbox

class BNFView : View("БНФ") {

    override val root = vbox {
        minWidth = 400.0
        minHeight = 300.0

        textarea {
            minWidth = 400.0
            minHeight = 300.0

            isEditable = false

            text =
                """
Язык = "Begin" Слагаемые Операторы "End"
Слагаемые = Слагаемое ... Слагаемое
Слагаемое = "Real" Перем ... Перем ! "Integer" Цел ... Цел
Операторы = Опер ";" ... Опер
Опер = Метка ":" Перем "=" Пр.часть
Пр.часть = </"-"/> блок1 ["+" ! "-"] ... блок1
блок1 = блок2 ["*" ! "/"] ... блок2
блок2 = блок3 ["&" ! "|"] ... блок3
блок3 = </!/> блок4
блок4 = Цел ! Перем ! "(" Пр.часть ")"
Метка = Цел
Перем = Буква</[Буква!Цифра]...[Буква!Цифра]/>
Буква = "A"!"B"!...!"Z"
Цел = Цифра...Цифра
Цифра = "0"!"1"!...!"7" 
                """
        }
    }
}