plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(ModuleConfig.compileSdk)

    defaultConfig {
        minSdkVersion(ModuleConfig.minSdk)
        targetSdkVersion(ModuleConfig.targetSdk)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    dataBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(project(":app"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.kotlinCoroutines}")
    implementation("androidx.appcompat:appcompat:${Version.androidxAppcompat}")
    implementation("androidx.constraintlayout:constraintlayout:${Version.androidxConstraintlayout}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Version.androidxLifecycle}")
    implementation("com.linecorp.lich:component:${Version.lichComponent}")
    implementation("com.linecorp.lich:viewmodel:${Version.lichViewmodel}")
    kapt("com.linecorp.lich:viewmodel-compiler:${Version.lichViewmodel}")
    compileOnly("com.google.auto.service:auto-service-annotations:${Version.autoService}")
    kapt("com.google.auto.service:auto-service:${Version.autoService}")
    implementation("com.google.dagger:dagger:${Version.dagger}")
    kapt("com.google.dagger:dagger-compiler:${Version.dagger}")
}
