buildscript {

    val kotlin_version by extra("1.5.10")
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://developer.huawei.com/repo/") // HUAWEI Maven repository
    }
    dependencies {

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${ClasspathVersion.versionNavigationArgs}")
        classpath("com.android.tools.build:gradle:${ClasspathVersion.versionGradleKotlin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${ClasspathVersion.versionKotlin}")
        classpath("com.huawei.agconnect:agcp:${ClasspathVersion.versionHuaweiService}")
        classpath("com.google.gms:google-services:${ClasspathVersion.versionGoogleService}")// HUAWEI agcp plugin
        classpath("com.bugsnag:bugsnag-android-gradle-plugin:${ClasspathVersion.versionBugsnack}")
        classpath("org.koin:koin-gradle-plugin:${ClasspathVersion.versionKoin}")

    }
}

allprojects {

    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://jitpack.io")
        maven(url = "https://developer.huawei.com/repo/") // HUAWEI Maven r
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://plugins.gradle.org/m2/")
        maven("https://ci.android.com/builds/submitted/5837096/androidx_snapshot/latest/repository")// epository
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}