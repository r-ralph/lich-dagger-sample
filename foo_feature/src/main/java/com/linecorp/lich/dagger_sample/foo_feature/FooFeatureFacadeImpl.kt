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
package com.linecorp.lich.dagger_sample.foo_feature

import android.content.Context
import android.content.Intent
import com.google.auto.service.AutoService
import com.linecorp.lich.component.ServiceLoaderComponent
import com.linecorp.lich.viewmodel.putViewModelArgs

/**
 * The implementation of [FooFeatureFacade].
 */
@AutoService(FooFeatureFacade::class)
class FooFeatureFacadeImpl : FooFeatureFacade, ServiceLoaderComponent {

    private lateinit var context: Context

    override fun init(context: Context) {
        this.context = context
    }

    override fun launchFooFeatureActivity(message: String) {
        val intent = Intent(context, FooFeatureActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putViewModelArgs(FooViewModelArgs(activityMessage = message))
        }
        context.startActivity(intent)
    }
}
