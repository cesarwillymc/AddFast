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

        BuildModules.Features.NAVHOST,  BuildModules.Features.HOME
    )
    /*
     ":feature:ubication",
        ":feature:advertisements",
        ":feature:authentification",
        ":feature:camerax",
,

        ":feature:offert",
        ":feature:postulate",
        ":feature:profile",
        ":feature:report",
     */

}

dependencies {
    //implementation(fileTree("libs") { include(listOf("*.aar")) })
    implementation(project(path = ":core"))


    //Kotlin
    implementation(Dependencies.KOTLIN)

    //Android
    implementation(Dependencies.APPCOMPACT)

    implementation(Dependencies.MATERIALDESING)
    //Busgnack
    implementation(Dependencies.BUGSNAG)
    /*
      implementation(Dependencies.CONSTRAINT)
    implementation(Dependencies.SUPPORTLEGACY)
    implementation(Dependencies.CARDVIEW)
    implementation(Dependencies.RECYCLERVIEW)
     */

    //DependencyInjection
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGERCOMPILER)
   /* //DB
    implementation(Dependencies.ROOMRUNTIME)
    kapt(Dependencies.ROOMCOMPILER)
    implementation(Dependencies.ROOMKTX)
    //UnitTest

    //Format Hour
    implementation(Dependencies.PRETTYTIME)



    //Shimer
    implementation(Dependencies.SHIMMER)

    //Karumi Permission
    implementation(Dependencies.KARUMI)

    //LiveDta

    implementation(Dependencies.LIVEDATA)
    implementation(Dependencies.COROUTINESSERVICES)
    implementation(Dependencies.LIFECYCLEKTX)


    // Preferences DataStore
    implementation("com.google.android.gms:play-services-maps:17.0.0")
    implementation("com.google.maps.android:android-maps-utils:2.2.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0-alpha02")
    implementation("com.huawei.hms:maps:5.0.1.300")
    //Location
    implementation("com.huawei.hms:location:5.0.0.302")
    //implementation(Dependencies.DATASTORE)
    //implementation(Dependencies.DATASTORECORE)

    implementation("androidx.datastore:datastore-core:1.0.0-alpha02")
    //Glide data

    implementation(Dependencies.GLIDE)
    kapt(Dependencies.GLIDECOMPILER)
    implementation(Dependencies.GLIDETRANSFORMATION)
*/
    //Navigation components
    implementation(Dependencies.NAVIGATIONFRAGMENT)
    implementation(Dependencies.NAVIGATIONUI)
/*
    //Huawei

    implementation(Dependencies.HUAWEIAUTH)
    implementation(Dependencies.HUAWEICORE)

    // Firebase
    implementation(Dependencies.FIRESTORE)
    implementation(Dependencies.FIREBASESTORAGE)


    //Camera x
    implementation(Dependencies.CAMERA)
    implementation(Dependencies.CAMERALIFECYCLE)
    implementation(Dependencies.CAMERAVIEW)
    implementation(Dependencies.CAMERACORE)


    //Retrofit
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.GSONCONVERTER)
    implementation(Dependencies.SCALARCONVERTER)


    //Gruie
    implementation(Dependencies.GROUPIE)
    implementation(Dependencies.GROUPIEEXTENSION)

    //Gpuimage
    implementation(Dependencies.GPUIMAGE)

    //MAPSUTIL
    implementation(Dependencies.MAPSUTIL)
    //KLAXON
    implementation(Dependencies.KLAXON)
    //CIRCLEIMAGE
    implementation(Dependencies.CIRCLEIMAGE)
    //SWIPEDECORATOR
    implementation(Dependencies.SWIPEDECORATOR)
    //RX
    implementation(Dependencies.RXANDROID)
    implementation(Dependencies.RXJAVA)*/

}