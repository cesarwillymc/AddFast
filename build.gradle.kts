import extension.applyDefault
buildscript {


    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://developer.huawei.com/repo/") //
    }
    dependencies {

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${ClasspathVersion.versionNavigationArgs}")
        classpath("com.android.tools.build:gradle:${ClasspathVersion.versionGradleKotlin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${ClasspathVersion.versionKotlin}")
        classpath("com.huawei.agconnect:agcp:${ClasspathVersion.versionHuaweiService}")
        classpath("com.google.gms:google-services:${ClasspathVersion.versionGoogleService}")// HUAWEI agcp plugin
        classpath("com.bugsnag:bugsnag-android-gradle-plugin:${ClasspathVersion.versionBugsnack}")


    }
}

allprojects {
    repositories.applyDefault()

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}