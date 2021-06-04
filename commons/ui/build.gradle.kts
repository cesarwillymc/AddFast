import dependency.Dependencies
import extension.implementation
plugins {
    id("commons.android-library")
}

dependencies {
    implementation(Dependencies.LIFECYCLEKTX)

    implementation(Dependencies.CONSTRAINT)
    implementation(Dependencies.RECYCLERVIEW)
    implementation(Dependencies.COREKTX)
    implementation(Dependencies.NAVIGATIONFRAGMENT)
    implementation(Dependencies.NAVIGATIONUI)
    implementation(Dependencies.FRAGMENT_KTX)
    kapt(Dependencies.DATABINDING)
}
