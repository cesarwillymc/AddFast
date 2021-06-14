import dependency.Dependencies
import extension.implementation
import extension.kapt
plugins {
    id("commons.android-library")
}

dependencies {
    implementation(Dependencies.LIVEDATA)
    implementation(Dependencies.LIFECYCLEKTX)
    implementation(Dependencies.CONSTRAINT)
    implementation(Dependencies.RECYCLERVIEW)
    implementation(Dependencies.COREKTX)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.NAVIGATIONFRAGMENT)
    implementation(Dependencies.NAVIGATIONUI)
    implementation(Dependencies.GLIDE)
    kapt(Dependencies.GLIDECOMPILER)
    implementation(Dependencies.GLIDETRANSFORMATION)
    implementation(Dependencies.PRETTYTIME)
    implementation(Dependencies.CIRCLEIMAGE)

    kapt(Dependencies.DATABINDING)
}
