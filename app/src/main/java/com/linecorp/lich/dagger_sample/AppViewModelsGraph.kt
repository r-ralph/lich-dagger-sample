package com.linecorp.lich.dagger_sample

import com.linecorp.lich.dagger_sample.mvvm.SampleViewModel
import com.linecorp.lich.viewmodel.SavedState
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * A Dagger [Subcomponent] that instantiates ViewModels in the `app` module.
 */
@Subcomponent
interface AppViewModelsGraph {

    fun sampleViewModel(): SampleViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance savedState: SavedState): AppViewModelsGraph
    }
}
