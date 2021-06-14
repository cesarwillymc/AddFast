import dependency.Dependencies
import extension.implementation
import extension.kapt

plugins {
    id("commons.android-library")
}

dependencies {
    implementation(project(BuildModules.Commons.UI))
    implementation(project(BuildModules.CORE))
    implementation(Dependencies.CONSTRAINT)
    implementation(Dependencies.NAVIGATIONFRAGMENT)
    implementation(Dependencies.NAVIGATIONUI)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation(Dependencies.LOTTIE)
    kapt(Dependencies.DATABINDING)
}
