import dependency.Dependencies
import extension.implementation

plugins {
    id("commons.android-dynamic-feature")

}

dependencies {
    implementation(project(BuildModules.Features.NAVHOST))
}
