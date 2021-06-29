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

    suspend fun insertUser(usuario: Usuario)

    fun updateUser(usuario: Usuario)

    fun deleteUser()

    fun getUserStatic(): Usuario?

    fun getUserTimeReal(): LiveData<Usuario>

}