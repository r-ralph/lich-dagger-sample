package com.linecorp.lich.dagger_sample.mvvm.stepper

class SingleUpCounterValueStepper :
    CounterValueStepper {
    override fun calculate(currentValue: Int, delta: Int): Int = currentValue + delta
}
