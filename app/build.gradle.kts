import dependency.Dependencies

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.huawei.agconnect" ) // HUAWEI agconnect Gradle plugin
    id("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("androidx.navigation.safeargs.kotlin")
    id ("com.google.gms.google-services")
    id ("com.bugsnag.android.gradle")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId ="com.summit.android.addfast"
        minSdkVersion (23)
        targetSdkVersion (30)
        versionCode =1
        versionName ="0.001"
        resConfigs ("en", "zh-rCN")
        testInstrumentationRunner ="androidx.test.runner.AndroidJUnitRunner"
    }
    lintOptions {

        isCheckReleaseBuilds =false

    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled=false
            isShrinkResources=false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug"){
            isDebuggable =true
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
/*
ext {
    anko_version = "0.10.8"
    versionAndroid = "1.0.0"
    designVersion = "28.0.0"
    glideVersion = "4.9.0"
    retrofitVersion = "2.6.0"
    room_version = "2.2.5"
}
 */
dependencies {
    implementation(fileTree("libs"){include(listOf("*.aar"))})
    //Kotlin
    implementation (Dependencies.KOTLIN)
    implementation (Dependencies.COREKTX)

    //Android
    implementation (Dependencies.APPCOMPACT)
    implementation (Dependencies.CONSTRAINT)
    implementation (Dependencies.SUPPORTLEGACY)
    implementation (Dependencies.CARDVIEW)
    implementation (Dependencies.RECYCLERVIEW)
    implementation (Dependencies.MATERIALDESING)

    //DependencyInjection
    implementation (Dependencies.KOINVM)
    implementation (Dependencies.KOINFRAGMENT)

    //DB


    //UnitTest

    //Format Hour
    implementation (Dependencies.PRETTYTIME)
    //Busgnack
    implementation (Dependencies.BUGSNAG)


    //Shimer
    implementation (Dependencies.SHIMMER)

    //Karumi Permission
    implementation (Dependencies.KARUMI)

    //LiveDta

    implementation (Dependencies.LIVEDATA)
    implementation (Dependencies.COROUTINESANDROID)
    implementation (Dependencies.LIFECYCLEKTX)

    // Preferences DataStore
    
    implementation (Dependencies.DATASTORE)
    implementation (Dependencies.DATASTORECORE)


    //Glide data
    //glide
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    implementation ("com.google.android.gms:play-services-maps:17.0.1")
    kapt ("com.github.bumptech.glide:compiler:4.9.0")
    implementation ("jp.wasabeef:glide-transformations:4.3.0")
    // If you want to use the GPU Filters
    implementation ("jp.co.cyberagent.android:gpuimage:2.1.0")
    implementation ("com.google.firebase:firebase-firestore:23.0.0")
    implementation ("com.google.firebase:firebase-storage:20.0.0")



    //Navigation components
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.5")

    //Kodein
    implementation ("org.kodein.di:kodein-di-generic-jvm:6.3.2")
    implementation ("org.kodein.di:kodein-di-framework-android-x:6.3.2")

    //Unit Test Android
    androidTestImplementation ("androidx.test.ext:junit:1.1.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
    testImplementation ("junit:junit:4.+")

    implementation ("androidx.room:room-runtime:2.3.0")
    kapt ("androidx.room:room-compiler:2.3.0")
    // For Kotlin use kapt instead of annotationProcessor

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:2.3.0")

    //Huawei Messaging
    implementation ("com.huawei.hms:hianalytics:5.0.3.300")
    implementation ("com.huawei.agconnect:agconnect-appmessaging:1.4.1.300")

    //Cloud Storage
    implementation ("com.huawei.agconnect:agconnect-core:1.4.1.300")

    //Location
    implementation ("com.huawei.hms:location:5.0.0.302")

    //Driving
    implementation ("com.huawei.hms:drive:5.0.0.300")
    implementation ("com.huawei.hms:hwid:4.0.0.300")

    //Maps Huawei
    implementation ("com.huawei.hms:maps:5.0.1.300")

    //Auth Huawei
    implementation ("com.huawei.agconnect:agconnect-auth:1.4.1.300")

    //Camera Kit
    // implementation group: "com.huawei.multimedia", name: "camerakit", version: "1.1.3", ext: "aar"
    implementation ("com.beust:klaxon:5.0.1")
    //Camera x
    implementation ("androidx.camera:camera-core:1.1.0-alpha04")
    //CIRCLE
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    // CameraX Camera2 extensions
    implementation ("androidx.camera:camera-camera2:1.1.0-alpha04")

    // CameraX Lifecycle library
    implementation( "androidx.camera:camera-lifecycle:1.1.0-alpha04")

    // CameraX View class
    implementation ("androidx.camera:camera-view:1.0.0-alpha24")


    //Agora
    implementation ("io.agora.rtc:voice-sdk:3.0.0")


    //retrofit
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.7.2")
    //Parseo de JSON
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")

    //Swipe Decorator
    implementation ("it.xabaras.android:recyclerview-swipedecorator:1.2.3")
    //Smile Rv
    implementation ("com.github.sujithkanna:smileyrating:2.0.0")

    //Gruie
    implementation ("com.xwray:groupie:2.8.1")
    implementation ("com.xwray:groupie-kotlin-android-extensions:2.8.1")

    //Mpas Uitl
    implementation ("com.google.maps.android:android-maps-utils:2.2.0")

    //ViewPager2
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("android.arch.paging:runtime:1.0.1")



    //Reactive
    implementation ("io.reactivex.rxjava2:rxjava:2.2.10")
    implementation( "io.reactivex.rxjava2:rxandroid:2.1.1")
}