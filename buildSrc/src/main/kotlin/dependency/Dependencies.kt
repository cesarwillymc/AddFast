package dependency

object Dependencies {


    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib:${ClasspathVersion.versionKotlin}"
    const val COREKTX = "androidx.core:core-ktx:${DependencyVersion.COREKTX}"
    const val APPCOMPACT = "androidx.appcompat:appcompat:${DependencyVersion.APPCOMPACT}"
    const val DAGGER = "com.google.dagger:dagger:${DependencyVersion.DAGGER}"
    const val DAGGERCOMPILER = "com.google.dagger:dagger-compiler:${DependencyVersion.DAGGER}"
    const val CONSTRAINT = "androidx.constraintlayout:constraintlayout:${DependencyVersion.CONSTRAINT}"
    const val SUPPORTLEGACY = "androidx.legacy:legacy-support-v4:${DependencyVersion.SUPPORTLEGACY}"
    const val CARDVIEW = "androidx.cardview:cardview:${DependencyVersion.CARDVIEW}"
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:${DependencyVersion.RECYCLERVIEW}"
    const val MATERIALDESING = "com.google.android.material:material:${DependencyVersion.MATERIALDESING}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${DependencyVersion.FRAGMENT_KTX}"
    const val DATABINDING = "com.android.databinding:compiler:${DependencyVersion.DATABINDING}"
    const val BUGSNAG = "com.bugsnag:bugsnag-android:${ClasspathVersion.versionBugsnack}"
    const val SHIMMER = "com.facebook.shimmer:shimmer:${DependencyVersion.SHIMMER}"
    const val KARUMI = "com.karumi:dexter:${DependencyVersion.KARUMI}"
    const val PRETTYTIME = "org.ocpsoft.prettytime:prettytime:${DependencyVersion.PRETTYTIME}"
    const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${DependencyVersion.LIVEDATA}"
    const val COROUTINESSERVICES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${DependencyVersion.LIFECYCLEEXTENSION}"
    const val LIFECYCLEKTX = "androidx.lifecycle:lifecycle-runtime-ktx:${DependencyVersion.LIFECYCLEKTX}"
    const val COROUTINESANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersion.COROUTINESANDROID}"

    const val DATASTORE = "androidx.datastore:datastore-preferences:${DependencyVersion.DATASTORE}"
    const val DATASTORECORE = "androidx.datastore:datastore-core:${DependencyVersion.DATASTORE}"
    const val GLIDE = "com.github.bumptech.glide:glide:${DependencyVersion.GLIDE}"
    const val GLIDECOMPILER = "com.github.bumptech.glide:compiler:${DependencyVersion.GLIDECOMPILER}"
    const val GLIDETRANSFORMATION = "jp.wasabeef:glide-transformations:${DependencyVersion.GLIDETRANSFORMATION}"
    const val NAVIGATIONFRAGMENT = "androidx.navigation:navigation-fragment-ktx:${DependencyVersion.NAVIGATION}"
    const val NAVIGATIONUI = "androidx.navigation:navigation-ui-ktx:${DependencyVersion.NAVIGATION}"
    const val ROOMRUNTIME = "androidx.room:room-runtime:${DependencyVersion.ROOM}"
    const val ROOMCOMPILER = "androidx.room:room-compiler:${DependencyVersion.ROOM}"
    const val ROOMKTX = "androidx.room:room-ktx:${DependencyVersion.ROOM}"
    const val HUAWEIAUTH = "com.huawei.agconnect:agconnect-auth:${DependencyVersion.HUAWEI}"
    const val HUAWEICORE = "com.huawei.agconnect:agconnect-core:${DependencyVersion.HUAWEI}"
    const val FIRESTORE = "com.google.firebase:firebase-firestore:${DependencyVersion.FIRESTORE}"
    const val FIREBASESTORAGE = "com.google.firebase:firebase-storage:${DependencyVersion.FIREBASESTORAGE}"
    const val CAMERA = "androidx.camera:camera-camera2:${DependencyVersion.CAMERAX}"
    const val CAMERALIFECYCLE = "androidx.camera:camera-lifecycle:${DependencyVersion.CAMERAX}"
    const val CAMERAVIEW = "androidx.camera:camera-view:${DependencyVersion.CAMERAXVIEW}"
    const val CAMERACORE = "androidx.camera:camera-core:${DependencyVersion.CAMERAX}"
    const val RETROFIT = "com.squareup.retrofit2:adapter-rxjava2:${DependencyVersion.RETROFIT}"
    const val GSONCONVERTER = "com.squareup.retrofit2:converter-gson:${DependencyVersion.CONVERTER}"
    const val SCALARCONVERTER = "com.squareup.retrofit2:converter-scalars:${DependencyVersion.CONVERTER}"
    const val GROUPIE = "com.xwray:groupie:${DependencyVersion.GROUPIE}"
    const val GROUPIEEXTENSION = "com.xwray:groupie-kotlin-android-extensions:${DependencyVersion.GROUPIE}"
    const val GPUIMAGE = "jp.co.cyberagent.android:gpuimage:${DependencyVersion.GPUIMAGE}"
    const val MAPSUTIL = "com.google.maps.android:android-maps-utils:${DependencyVersion.MAPSUTIL}"
    const val KLAXON = "com.beust:klaxon:${DependencyVersion.KLAXON}"
    const val CIRCLEIMAGE = "de.hdodenhof:circleimageview:${DependencyVersion.CIRCLEIMAGE}"
    const val SWIPEDECORATOR = "it.xabaras.android:recyclerview-swipedecorator:${DependencyVersion.SWIPEDECORATOR}"
    const val RXANDROID = "io.reactivex.rxjava2:rxandroid:${DependencyVersion.RXANDROID}"
    const val RXJAVA = "io.reactivex.rxjava2:rxjava:${DependencyVersion.RXJAVA}"


    const val COIL = "io.coil-kt:coil:${DependencyVersion.COIL}"

}