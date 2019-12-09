package com.linecorp.lich.dagger_sample.foo_feature

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linecorp.lich.component.ComponentFactory

class FooRepository(private val context: Context) {

    fun getFooData(): LiveData<String> =
        MutableLiveData(context.getString(R.string.foo_data))

    companion object : ComponentFactory<FooRepository>() {
        override fun createComponent(context: Context): FooRepository =
            FooRepository(context)
    }
}
