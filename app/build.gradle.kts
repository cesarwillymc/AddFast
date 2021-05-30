import dependency.Dependencies

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.huawei.agconnect") // HUAWEI agconnect Gradle plugin
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("com.bugsnag.android.gradle")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "com.summit.android.addfast"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "0.001"
        resConfigs("en", "zh-rCN")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    lintOptions {

        isCheckReleaseBuilds = false

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
        }

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