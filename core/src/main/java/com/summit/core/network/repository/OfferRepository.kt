package com.summit.core.network.repository

import android.net.Uri
import com.summit.core.network.model.Promociones
import com.summit.core.status.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface OfferRepository {
    suspend fun getAllPromociones(): List<Promociones>
    suspend fun getUrlDownloadFile(path: String): String
    suspend fun uploadFotoPromocion(imagen: File) : Flow<Resource<String>>
    suspend fun crearPromocion(model:Promociones): Unit
    suspend fun desactivarPromocion(id:String): Unit
    suspend fun getPromocion(): List<Promociones>

}