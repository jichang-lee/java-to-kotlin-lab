package com.group.libraryapp.calculator

fun main() {
    val calculatorTest = CalculatorTest()
    calculatorTest.addTest()
}

class CalculatorTest {

    fun addTest() {
        val calculator = Calculator(5)
        calculator.add(3)

        val resultCalculator = Calculator(8)

        if(calculator != resultCalculator) {
            throw IllegalStateException()
        }
    }

}