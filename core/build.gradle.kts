import dependency.Dependencies
import extension.buildConfigBooleanField
import extension.buildConfigIntField
import extension.buildConfigStringField
import extension.implementation
import extension.kapt

plugins {
    id("commons.android-library")
}


android {

    buildTypes.forEach {

        try{
            it.buildConfigBooleanField("ADDFAST_DATABASE_EXPORT_SCHEMA", false)
            it.buildConfigStringField( "ADDFAST_DATABASE_NAME", "add_fast-db")
            it.buildConfigIntField( "ADDFAST_DATABASE_VERSION", 1)
        }catch (ignored: Exception){
            throw InvalidUserDataException()
        }

    }

}

dependencies {
    implementation(Dependencies.ROOMKTX)
    implementation(Dependencies.ROOMRUNTIME)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.GSONCONVERTER)
    implementation(Dependencies.HUAWEIAUTH)
    implementation(Dependencies.HUAWEICORE)
    implementation(Dependencies.FIREBASESTORAGE)
    implementation(Dependencies.FIRESTORE)
    implementation(Dependencies.KLAXON)
    kapt(Dependencies.ROOMCOMPILER)
}
