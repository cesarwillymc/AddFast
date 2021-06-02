package com.summit.core.network.repository

import androidx.lifecycle.LiveData
import com.summit.core.network.model.Reporte
import com.summit.core.network.model.Usuario
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface UserRepository {

    //Get link image storage
    suspend fun getUrlDownloadFile(path:String): String

    //CRUD BASICO DB BASE INSERT, DELETE, UPDATE, SELECT

    fun insertUser(usuario: Usuario)

    fun updateUser(usuario: Usuario)

    fun deleteUser()

    fun getUserStatic(): Usuario

    fun getUserTimeReal(): LiveData<Usuario>

    suspend fun disableAccount( id:String)
    suspend fun activeAccount( id:String)
    suspend fun disableAdmin( id:String)
    suspend fun addAdmin( id:String)
    suspend fun crearReporte(reporte: Reporte)

    suspend fun reportarUsuario(iduser: String)
}