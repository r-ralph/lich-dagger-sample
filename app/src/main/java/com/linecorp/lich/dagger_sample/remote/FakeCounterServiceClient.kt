package com.linecorp.lich.dagger_sample.remote

internal class FakeCounterServiceClient : CounterServiceClient {
    override suspend fun getInitialCounterValue(counterName: String): Int = 50
}
