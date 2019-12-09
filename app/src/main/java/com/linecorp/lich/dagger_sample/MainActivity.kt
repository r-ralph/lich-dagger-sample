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
package com.linecorp.lich.dagger_sample

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.linecorp.lich.component.component
import com.linecorp.lich.dagger_sample.foo_feature.FooFeatureFacade
import com.linecorp.lich.dagger_sample.mvvm.MvvmSampleActivity

class MainActivity : AppCompatActivity() {

    private val fooFeatureFacade by component(FooFeatureFacade)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    fun launchMvvmSampleActivity(@Suppress("UNUSED_PARAMETER") view: View) {
        startActivity(MvvmSampleActivity.newIntent(this, "mvvm"))
    }

    fun launchFooFeatureActivity(@Suppress("UNUSED_PARAMETER") view: View) {
        val messageText = findViewById<EditText>(R.id.message_for_foo)
        val message = messageText?.text?.toString() ?: ""
        fooFeatureFacade.launchFooFeatureActivity(message)
    }
}
