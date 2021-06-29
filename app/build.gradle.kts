import dependency.Dependencies
import extension.implementation

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.HUAWEI)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.NAVIGATION_SAFE_ARGS)
    id(BuildPlugins.GOOGLE_SERVICE)
    id(BuildPlugins.BUGSNAG)
}

android {
    compileSdkVersion(BuildAndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
        minSdkVersion(BuildAndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(BuildAndroidConfig.TARGET_SDK_VERSION)

        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME

        vectorDrawables.useSupportLibrary = BuildAndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
        resConfigs(BuildAndroidConfig.RES_LANGUAGES)
        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName(BuildType.DEBUG) {

            isShrinkResources = BuildTypeDebug.isShinkResource
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
        }

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
    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    buildFeatures {
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    dynamicFeatures = mutableSetOf(

        BuildModules.Features.NAVHOST,
        BuildModules.Features.HOME,
        BuildModules.Features.PROFILE,
        BuildModules.Features.AUTHENTIFICATION,
        BuildModules.Features.CAMERAX,
        BuildModules.Features.POSTULATE,
        BuildModules.Features.OFFERT, BuildModules.Features.POSTULATEADD
    )

}

dependencies {

    implementation(project(path = ":core"))

    //Kotlin
    implementation(Dependencies.KOTLIN)

    //Android
    implementation(Dependencies.APPCOMPACT)

    implementation(Dependencies.MATERIALDESING)
    //Busgnack
    implementation(Dependencies.BUGSNAG)
    implementation(Dependencies.CONSTRAINT)
    //DependencyInjection
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGERCOMPILER)
    //Navigation components
    implementation(Dependencies.NAVIGATIONFRAGMENT)
    implementation(Dependencies.NAVIGATIONUI)
    implementation(Dependencies.NAVIGATIONDYNAMIC)
    implementation(Dependencies.NAVIGATION_RUNTIME)


}