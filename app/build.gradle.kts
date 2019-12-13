plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(ModuleConfig.compileSdk)

    defaultConfig {
        minSdkVersion(ModuleConfig.minSdk)
        targetSdkVersion(ModuleConfig.targetSdk)
        versionCode = ModuleConfig.versionCode
        versionName = ModuleConfig.versionName
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    packagingOptions {
        exclude("/META-INF/*.kotlin_module")
        exclude("**/*.kotlin_builtins")
        exclude("**/*.kotlin_metadata")
    }

    dynamicFeatures = mutableSetOf(":foo_feature")
}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.kotlinCoroutines}")
    implementation("androidx.appcompat:appcompat:${Version.androidxAppcompat}")
    implementation("androidx.constraintlayout:constraintlayout:${Version.androidxConstraintlayout}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Version.androidxLifecycle}")
    implementation("androidx.room:room-ktx:${Version.androidxRoom}")
    kapt("androidx.room:room-compiler:${Version.androidxRoom}")
    implementation("com.linecorp.lich:component:${Version.lichComponent}")
    implementation("com.linecorp.lich:viewmodel:${Version.lichViewmodel}")
    kapt("com.linecorp.lich:viewmodel-compiler:${Version.lichViewmodel}")
    compileOnly("com.google.auto.service:auto-service-annotations:${Version.autoService}")
    kapt("com.google.auto.service:auto-service:${Version.autoService}")
    implementation("com.google.dagger:dagger:${Version.dagger}")
    kapt("com.google.dagger:dagger-compiler:${Version.dagger}")

    debugRuntimeOnly("com.linecorp.lich:component-debug:${Version.lichComponent}")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlin}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.kotlinCoroutines}")
    testImplementation("androidx.test:runner:${Version.androidxTestRunner}")
    testImplementation("androidx.test.ext:junit:${Version.androidxTestJunit}")
    testImplementation("androidx.test.espresso:espresso-core:${Version.androidxTestEspresso}")
    testImplementation("com.linecorp.lich:component-test-mockk:${Version.lichComponent}")
    testImplementation("com.linecorp.lich:viewmodel-test-mockk:${Version.lichViewmodel}")
    testImplementation("io.mockk:mockk:${Version.mockk}")
    testImplementation("org.robolectric:robolectric:${Version.robolectric}")
}
