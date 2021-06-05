import dependency.Dependencies
import extension.implementation
import extension.kapt

plugins {
    id("commons.android-library")
}

dependencies {
    implementation(project(BuildModules.Commons.UI))

    implementation(Dependencies.CONSTRAINT)
    implementation(Dependencies.NAVIGATIONFRAGMENT)
    implementation(Dependencies.NAVIGATIONUI)
    implementation(Dependencies.FRAGMENT_KTX)

    kapt(Dependencies.DATABINDING)
}
