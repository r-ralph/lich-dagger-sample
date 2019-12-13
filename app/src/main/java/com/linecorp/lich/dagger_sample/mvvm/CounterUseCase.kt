/*
 * Copyright 2019 LINE Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linecorp.lich.dagger_sample.mvvm

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.linecorp.lich.dagger_sample.app.FeatureFlag
import com.linecorp.lich.dagger_sample.entity.Counter
import com.linecorp.lich.dagger_sample.mvvm.stepper.CounterValueStepper
import com.linecorp.lich.dagger_sample.mvvm.stepper.DoubleUpCounterValueStepper
import com.linecorp.lich.dagger_sample.mvvm.stepper.SingleUpCounterValueStepper
import com.linecorp.lich.dagger_sample.repository.CounterRepository
import com.linecorp.lich.dagger_sample.repository.CounterResult
import javax.inject.Inject

@MainThread
class CounterUseCase @Inject constructor(
    private val counterRepository: CounterRepository
) {
    val liveCounter: MutableLiveData<Counter?> = MutableLiveData(null)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val stepper: CounterValueStepper = if (FeatureFlag.DOUBLE_UP_COUNTER) {
        DoubleUpCounterValueStepper()
    } else {
        SingleUpCounterValueStepper()
    }

    suspend fun loadCounter(counterName: String) {
        isLoading.value = true

        when (val result = counterRepository.getCounter(counterName)) {
            is CounterResult.Success ->
                liveCounter.value = result.counter
            CounterResult.NetworkError -> {
                liveCounter.value = null
                // TODO: Show error message.
            }
        }

        isLoading.value = false
    }

    suspend fun changeCounterValue(delta: Int) {
        liveCounter.value?.let { counter ->
            val newCounter = counter.copy(value = stepper.calculate(counter.value, delta))
            liveCounter.value = newCounter
            counterRepository.storeCounter(newCounter)
        }
    }

    suspend fun deleteCounter() {
        liveCounter.value?.let { counter ->
            liveCounter.value = null
            counterRepository.deleteCounter(counter)
        }
    }
}
