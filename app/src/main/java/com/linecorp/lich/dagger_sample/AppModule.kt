package com.linecorp.lich.dagger_sample

import android.content.Context
import androidx.room.Room
import com.linecorp.lich.dagger_sample.db.CounterDao
import com.linecorp.lich.dagger_sample.db.CounterDatabase
import com.linecorp.lich.dagger_sample.remote.CounterServiceClient
import com.linecorp.lich.dagger_sample.remote.FakeCounterServiceClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideCounterDatabase(context: Context): CounterDatabase =
        Room.databaseBuilder(context, CounterDatabase::class.java, "counter").build()

    @Provides
    fun provideCounterDao(counterDatabase: CounterDatabase): CounterDao =
        counterDatabase.counterDao

    @Singleton
    @Provides
    fun provideCounterServiceClient(): CounterServiceClient = FakeCounterServiceClient()
}
