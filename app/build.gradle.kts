import dependency.Dependencies

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.HUAWEI) // HUAWEI agconnect Gradle plugin
    id(BuildPlugins.KOTLIN_PARCELIZE)
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
        resConfigs(BuildAndroidConfig.RES_LANGUAGES)
        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled
            isShrinkResources = BuildTypeRelease.isShinkResource

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName(BuildType.DEBUG) {
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
        }

    }
    flavorDimensions(BuildProductDimensions.ENVIRONMENT)
    productFlavors {
        ProductFlavorDevelop.appCreate(this)
        ProductFlavorQA.appCreate(this)
        ProductFlavorProduction.appCreate(this)
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
}

dependencies {
    implementation(fileTree("libs") { include(listOf("*.aar")) })
    //Kotlin
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.COREKTX)

    //Android
    implementation(Dependencies.APPCOMPACT)
    implementation(Dependencies.CONSTRAINT)
    implementation(Dependencies.SUPPORTLEGACY)
    implementation(Dependencies.CARDVIEW)
    implementation(Dependencies.RECYCLERVIEW)
    implementation(Dependencies.MATERIALDESING)

    //DependencyInjection
    implementation(Dependencies.KOINVM)
    implementation(Dependencies.KOINFRAGMENT)
    implementation(Dependencies.KOINGENERIC)
    implementation(Dependencies.KOINGENERICANDROID)

    //DB
    implementation(Dependencies.ROOMRUNTIME)
    kapt(Dependencies.ROOMCOMPILER)
    implementation(Dependencies.ROOMKTX)
    //UnitTest

    //Format Hour
    implementation(Dependencies.PRETTYTIME)
    //Busgnack
    implementation(Dependencies.BUGSNAG)


    //Shimer
    implementation(Dependencies.SHIMMER)

    //Karumi Permission
    implementation(Dependencies.KARUMI)

    //LiveDta

    implementation(Dependencies.LIVEDATA)
    implementation(Dependencies.COROUTINESSERVICES)
    implementation(Dependencies.LIFECYCLEKTX)


    // Preferences DataStore

    implementation(Dependencies.DATASTORE)
    implementation(Dependencies.DATASTORECORE)


    //Glide data

    implementation(Dependencies.GLIDE)
    kapt(Dependencies.GLIDECOMPILER)
    implementation(Dependencies.GLIDETRANSFORMATION)

    //Navigation components
    implementation(Dependencies.NAVIGATIONFRAGMENT)
    implementation(Dependencies.NAVIGATIONUI)

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
    implementation(Dependencies.RXJAVA)

}