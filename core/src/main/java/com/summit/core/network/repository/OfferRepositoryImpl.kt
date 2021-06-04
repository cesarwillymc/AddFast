package com.summit.core.network.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.AppDB
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.exception.ExceptionGeneral
import com.summit.core.network.model.Promociones
import com.summit.core.status.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.io.File
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

    override suspend fun uploadFotoPromocion(imagen: File) = callbackFlow<Resource<String>> {
        val path = "images/${imagen.name}"
        val storageReference = storage.getReference(path)
        //,attribute,100L
        val uploadTask = storageReference.putFile(Uri.fromFile(imagen))
        uploadTask.addOnProgressListener {
            val total = ((it.bytesTransferred * 1.0) / imagen.length())
            offer(Resource.loading(total))
        }.addOnSuccessListener {
            offer(Resource.success(path))
        }.addOnFailureListener {
            offer(Resource.error(ExceptionGeneral(it.message?:"")))
        }
        awaitClose {
            uploadTask.cancel()
        }
    }

    override suspend fun crearPromocion(model: Promociones): Unit {
        val ubicacion = db.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("promocion").add(model).await()
        firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("promocion").document(anuncios.id)
            .update("id", anuncios.id).await()
        return Unit
    }

    override suspend fun desactivarPromocion(id: String): Unit {
        val ubicacion = db.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("promocion").document(id)
            .update("state", false).await()
        return Unit
    }

    override suspend fun getPromocion(): List<Promociones> {
        val ubicacion = db.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("promocion").whereEqualTo("state", true)
            .get().await()
        return anuncios.toObjects(Promociones::class.java)
    }
}