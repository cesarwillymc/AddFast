package com.summit.core.network.repository

import android.net.Uri
import android.util.Log
import com.beust.klaxon.Klaxon
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.AppDB
import com.summit.core.exception.ExceptionGeneral
import com.summit.core.json.Constants
import com.summit.core.network.model.Anuncios
import com.summit.core.status.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.*

internal class AdRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val db: AppDB,
    private val storage: FirebaseStorage
) : AdRepository {

    override suspend fun getAnuncioId(id: String): Anuncios {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val dato = firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").document(id).get().await()
        return dato.toObject(Anuncios::class.java)!!
    }

    override suspend fun getAllAnunciosByPalabra(palabra: String): List<Anuncios> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios")
            .whereEqualTo("estado", "PENDIENTE").get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }

    override suspend fun getAllAnuncios(): List<Anuncios> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios")
            .whereEqualTo("estado", "PENDIENTE").get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }

     override suspend fun getAllAnunciosPost(): List<Anuncios> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios")
            .whereEqualTo("estado", "PUBLICADO").get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }

    override suspend fun cambiarEstadoAnuncio(id: String, message: String): Unit {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").document(id)
            .update("estado", message).await()
        return Unit
    }

    override suspend fun aumentarVisualizacionesAnuncios(id: String) {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").document(id)
            .update("visualizaciones", FieldValue.increment(1)).await()
        return Unit
    }

    override suspend fun reportarAnuncio(id: String) {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").document(id)
            .update("reporte", FieldValue.increment(1)).await()
        return Unit
    }

    override suspend fun crearAnuncio(anuncios: Anuncios, departamento: String, provincia: String) {
        val result = firestore.collection(departamento.trim().toLowerCase()).document(provincia.trim().toLowerCase())
            .collection("anuncios").add(anuncios).await()
        firestore.collection(departamento.trim().toLowerCase()).document(provincia.trim().toLowerCase())
            .collection("anuncios").document(result.id).update("id", result.id).await()
        return Unit
    }

    override suspend fun crearAnunciodata(departamento: String, provincia: String) {
        val datos: List<Anuncios>? = Klaxon().parseArray<Anuncios>(Constants.data)
        datos?.forEach {
            val result =
                firestore.collection(departamento.trim().toLowerCase()).document(provincia.trim().toLowerCase())
                    .collection("anuncios").add(it).await()
            firestore.collection(departamento.trim().toLowerCase()).document(provincia.trim().toLowerCase())
                .collection("anuncios").document(result.id).update("id", result.id).await()
        }

        return Unit
    }

    override suspend fun uploadFotoAnuncio(imagen: File) = callbackFlow<Resource<String>> {
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

    override suspend fun getMisAnuncios(id: String): List<Anuncios> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        Log.e(
            "getMisAnuncios",
            "${ubicacion.departamento.trim().toLowerCase()}   ${ubicacion.provincia.trim().toLowerCase()}"
        )
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").whereEqualTo("idempresa", id)
            .get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }
}