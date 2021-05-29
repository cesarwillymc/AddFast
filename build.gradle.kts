// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    //ext.kotlin_version = "1.3.61"
    //ext.koin_version = "2.2.2"
    repositories {
        google()
        jcenter()
        maven ( url ="https://developer.huawei.com/repo/" ) // HUAWEI Maven repository
    }
    dependencies {
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")
        classpath ("com.android.tools.build:gradle:4.1.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
        classpath ("com.huawei.agconnect:agcp:1.4.1.300")
        classpath ("com.google.gms:google-services:4.3.4")// HUAWEI agcp plugin
        classpath ("com.bugsnag:bugsnag-android-gradle-plugin:5.+")

        classpath ("org.koin:koin-gradle-plugin:2.2.2")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven (url= "https://jitpack.io")
        maven ( url = "https://developer.huawei.com/repo/" ) // HUAWEI Maven repository
    }
}

/*task clean(type: Delete) {

}*/
tasks.register("clean",Delete::class){
    delete( rootProject.buildDir)
}