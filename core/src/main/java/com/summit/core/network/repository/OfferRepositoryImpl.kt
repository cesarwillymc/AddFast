package com.summit.core.network.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.network.model.Promociones
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class OfferRepositoryImpl(
    private val db: UbicacionModelDao, private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : OfferRepository {
    override suspend fun getAllPromociones(): List<Promociones> {
        val ubicacion = db.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("promocion").whereEqualTo("state", true)
            .get().await()
        return anuncios.toObjects(Promociones::class.java)
    }

    override suspend fun getUrlDownloadFile(path: String): String = suspendCancellableCoroutine { continuation ->
        storage.getReference(path).downloadUrl.addOnSuccessListener {
            continuation.resume(it.toString())
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }




}