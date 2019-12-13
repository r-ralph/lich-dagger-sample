package com.linecorp.lich.dagger_sample.mvvm.stepper

class DoubleUpCounterValueStepper :
    CounterValueStepper {
    override fun calculate(currentValue: Int, delta: Int): Int = currentValue + delta * 2
}
