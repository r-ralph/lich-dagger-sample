package com.linecorp.lich.dagger_sample.foo_feature

import android.content.Context
import androidx.lifecycle.LiveData
import com.linecorp.lich.component.component
import com.linecorp.lich.viewmodel.AbstractViewModel
import com.linecorp.lich.viewmodel.Argument
import com.linecorp.lich.viewmodel.GenerateArgs
import com.linecorp.lich.viewmodel.SavedState
import com.linecorp.lich.viewmodel.ViewModelFactory

@GenerateArgs
class FooViewModel(context: Context, savedState: SavedState) : AbstractViewModel() {

    private val fooRepository by context.component(FooRepository)

    @Argument
    val activityMessage: LiveData<String> by savedState.liveData()

    val repositoryData: LiveData<String>
        get() = fooRepository.getFooData()

    companion object : ViewModelFactory<FooViewModel>() {
        override fun createViewModel(context: Context, savedState: SavedState): FooViewModel =
            FooViewModel(context, savedState)
    }
}
