
package commons

import BuildAndroidConfig
import BuildProductDimensions
import ProductFlavorDevelop
import ProductFlavorProduction
import ProductFlavorQA
import dependency.Dependencies
import extension.addTestsDependencies
import extension.implementation
import gradle.kotlin.dsl.accessors._bd5d340d956819462ab86b7cd6ba2c5e.kapt

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






}


dependencies {
    implementation(project(BuildModules.APP))
    implementation(project(BuildModules.CORE))
    implementation(project(BuildModules.Commons.UI))
    implementation(Dependencies.CONSTRAINT)
    implementation(Dependencies.MATERIALDESING)
    implementation(Dependencies.NAVIGATIONFRAGMENT)
    implementation(Dependencies.NAVIGATIONUI)
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGERCOMPILER)
    implementation(Dependencies.COROUTINESANDROID)
    implementation(Dependencies.COROUTINESSERVICES)
 //   testImplementation(project(BuildModules.Libraries.TEST_UTILS))
    //addTestsDependencies()
}
