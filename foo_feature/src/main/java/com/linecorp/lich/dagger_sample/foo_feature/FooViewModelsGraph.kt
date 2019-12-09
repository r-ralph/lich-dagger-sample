package com.linecorp.lich.dagger_sample.foo_feature

import com.linecorp.lich.viewmodel.SavedState
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * A Dagger [Subcomponent] that instantiates ViewModels in the `foo_feature` module.
 */
@Subcomponent
interface FooViewModelsGraph {

    fun fooViewModel(): FooViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance savedState: SavedState): FooViewModelsGraph
    }
}
