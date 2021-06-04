
package commons

import BuildAndroidConfig
import dependency.Dependencies
import extension.kapt
import extension.implementation

plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}


android {
    compileSdkVersion(BuildAndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(BuildAndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(BuildAndroidConfig.TARGET_SDK_VERSION)

        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    dataBinding {
        isEnabled = true
    }
    flavorDimensions(BuildProductDimensions.ENVIRONMENT)
    productFlavors {
        ProductFlavorDevelop.libraryCreate(this)
        ProductFlavorQA.libraryCreate(this)
        ProductFlavorProduction.libraryCreate(this)
    }

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
        getByName("test") {
            java.srcDir("src/test/kotlin")
        }
        getByName("androidTest") {
            java.srcDir("src/androidTest/kotlin")
        }
    }




}


dependencies {
    implementation(project(BuildModules.APP))
    implementation(project(BuildModules.CORE))
    implementation(project(BuildModules.Commons.UI))

    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.APPCOMPACT)
    implementation(Dependencies.COROUTINESSERVICES)
    implementation(Dependencies.COROUTINESANDROID)
    implementation(Dependencies.NAVIGATIONUI)
    implementation(Dependencies.NAVIGATIONFRAGMENT)
    implementation(Dependencies.LIFECYCLEKTX)
    implementation(Dependencies.LIVEDATA)
    implementation(Dependencies.COREKTX)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.CONSTRAINT)
    implementation(Dependencies.DAGGER)

    kapt(Dependencies.DAGGERCOMPILER)
    kapt(Dependencies.DATABINDING)
    kapt(Dependencies.ROOMCOMPILER)

 //   testImplementation(project(BuildModules.Libraries.TEST_UTILS))
    //addTestsDependencies()
}
