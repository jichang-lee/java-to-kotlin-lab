package com.group.libraryapp.calculator

class Calculator(
    private var _number: Int // " _ "코틀린 공식 컨벤션
) {
    val number: Int
        get() = _number

    fun add(operand: Int) {
        this._number += operand
    }

    fun minus(operand: Int) {
        this._number -= operand
    }

    fun multiply(operand: Int) {
        this._number *= operand
    }

    fun divide(operand: Int) {
        if (operand == 0) {
            throw IllegalArgumentException("0은 나눌 수 없습니다.")
        }
        this._number /= operand
    }

}