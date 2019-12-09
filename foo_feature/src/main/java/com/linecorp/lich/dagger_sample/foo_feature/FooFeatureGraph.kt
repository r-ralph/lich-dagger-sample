package com.linecorp.lich.dagger_sample.foo_feature

import android.content.Context
import com.linecorp.lich.component.ComponentFactory
import com.linecorp.lich.component.getComponent
import com.linecorp.lich.dagger_sample.ApplicationGraph
import dagger.Component

/**
 * A Dagger [Component] which is the root of the object graph in the `foo_feature` module.
 */
@FooFeatureScope
@Component(dependencies = [ApplicationGraph::class])
interface FooFeatureGraph {

    fun viewModelsGraphFactory(): FooViewModelsGraph.Factory

    @Component.Factory
    interface Factory {
        fun create(applicationGraph: ApplicationGraph): FooFeatureGraph
    }

    companion object : ComponentFactory<FooFeatureGraph>() {
        override fun createComponent(context: Context): FooFeatureGraph =
            DaggerFooFeatureGraph.factory().create(context.getComponent(ApplicationGraph))
    }
}
