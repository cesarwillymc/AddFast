import dependency.Dependencies
import extension.implementation
import extension.buildConfigStringField
plugins {
    id("commons.android-library")
}

dependencies {
    implementation(Dependencies.LIFECYCLEKTX)
    implementation(Dependencies.LIVEDATA)
    implementation(Dependencies.CONSTRAINT)
    implementation(Dependencies.RECYCLERVIEW)
    implementation(Dependencies.COREKTX)
    implementation(Dependencies.NAVIGATIONFRAGMENT)
    implementation(Dependencies.NAVIGATIONUI)
    implementation(Dependencies.COIL)
}
