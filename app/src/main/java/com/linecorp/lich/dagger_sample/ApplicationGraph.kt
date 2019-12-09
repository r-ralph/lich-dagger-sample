package com.linecorp.lich.dagger_sample

import android.content.Context
import com.linecorp.lich.component.ComponentFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * The root Dagger [Component] for this application.
 */
@Singleton
@Component(modules = [AppModule::class])
interface ApplicationGraph {

    fun viewModelsGraphFactory(): AppViewModelsGraph.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationGraph
    }

    companion object : ComponentFactory<ApplicationGraph>() {
        override fun createComponent(context: Context): ApplicationGraph =
            DaggerApplicationGraph.factory().create(context)
    }
}
