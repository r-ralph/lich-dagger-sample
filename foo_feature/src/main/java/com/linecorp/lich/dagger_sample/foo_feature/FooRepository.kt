package com.linecorp.lich.dagger_sample.foo_feature

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

@FooFeatureScope
class FooRepository @Inject constructor(private val context: Context) {

    fun getFooData(): LiveData<String> =
        MutableLiveData(context.getString(R.string.foo_data))
}
