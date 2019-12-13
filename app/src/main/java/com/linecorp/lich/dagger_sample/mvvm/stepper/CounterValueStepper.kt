package com.linecorp.lich.dagger_sample.mvvm.stepper

interface CounterValueStepper {
    fun calculate(currentValue: Int, delta: Int): Int
}
