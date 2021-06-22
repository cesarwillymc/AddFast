package com.summit.core.network.repository


import com.summit.core.network.model.SignIn
import com.summit.core.network.model.Usuario
import java.io.File

interface AuthRepository {


    //Enviar mensajes de texto a este numero
    suspend fun sendNumberCode( codeCountry: String, numberPhone: String):Unit
    //Verificar mensajes de texto
    suspend fun sendVerifyCode(credential: SignIn):String?
    //Create User WITH number
    suspend fun createUserVerifyCode(credential: SignIn):String
    //GET INFO
    suspend fun getDataInformation(id: String): Usuario?
    //CREATE DATA INFORMATION
    suspend fun createDataInformation(id: String, usuario: Usuario): Boolean

    suspend fun uploadImageProfile(imagen: File): String




}