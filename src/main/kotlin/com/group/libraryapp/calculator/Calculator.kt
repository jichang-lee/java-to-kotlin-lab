package com.group.libraryapp.calculator

class Calculator(
    var number: Int // " _ "코틀린 공식 컨벤션
) {

    fun add(operand: Int) {
        this.number += operand
    }

    fun minus(operand: Int) {
        this.number -= operand
    }

    fun multiply(operand: Int) {
        this.number *= operand
    }

    fun divide(operand: Int) {
        if (operand == 0) {
            throw IllegalArgumentException("0은 나눌 수 없습니다.")
        }
        this.number /= operand
    }

}