import dependency.Dependencies
import extension.*

plugins {
    id("commons.android-library")
}


android {

    buildTypes.forEach {

        try{
            it.buildConfigBooleanField("ADDFAST_DATABASE_EXPORT_SCHEMA", false)
            it.buildConfigStringField( "ADDFAST_DATABASE_NAME", "add_fast-db")
            it.buildConfigIntField( "ADDFAST_DATABASE_VERSION", 1)
            it.buildConfigStringField("FIREBASE_URL", getLocalProperty("firebase.urlproyect"))
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
    implementation(Dependencies.APPCOMPACT)
    implementation(Dependencies.HUAWEICORE)
    implementation(Dependencies.LOGGING)
    implementation(Dependencies.FIREBASESTORAGE)
    implementation(Dependencies.FIRESTORE)
    implementation(Dependencies.FIREBASEREALTIME)
    implementation(Dependencies.KLAXON)
    kapt(Dependencies.ROOMCOMPILER)
}
