package com.summit.core.network.repository

import android.net.Uri
import com.beust.klaxon.Klaxon
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.json.Constants
import com.summit.core.network.model.Anuncios
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.*

internal class AdRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val db: UbicacionModelDao,
    private val storage: FirebaseStorage
) : AdRepository {

    override suspend fun getAnuncioId(id: String): Anuncios {
        val ubicacion = db.selectUbicacionModelStatic()
        val dato = firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios").document(id).get().await()
        return dato.toObject(Anuncios::class.java)!!
    }

    override suspend fun getAllAnunciosByPalabra(palabra: String): List<Anuncios> {
        val ubicacion = db.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios")
            .whereEqualTo("estado", "PENDIENTE").get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }

    override suspend fun getAllAnuncios(): List<Anuncios> {
        val ubicacion = db.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios")
            .whereEqualTo("estado", "PENDIENTE").get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }

     override suspend fun getAllAnunciosPost(): List<Anuncios> {
        val ubicacion = db.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios")
            .whereEqualTo("estado", "PUBLICADO").get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }

    override suspend fun cambiarEstadoAnuncio(id: String, message: String) {
        val ubicacion = db.selectUbicacionModelStatic()
        firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios").document(id)
            .update("estado", message).await()
    }

    override suspend fun aumentarVisualizacionesAnuncios(id: String) {
        val ubicacion = db.selectUbicacionModelStatic()
        firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios").document(id)
            .update("visualizaciones", FieldValue.increment(1)).await()
    }

    override suspend fun reportarAnuncio(id: String) {
        val ubicacion = db.selectUbicacionModelStatic()
        firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios").document(id)
            .update("reporte", FieldValue.increment(1)).await()
    }

    override suspend fun crearAnuncio(anuncios: Anuncios, departamento: String, provincia: String) {
        val result = firestore.collection(departamento.trim().toLowerCase(Locale.ROOT)).document(provincia.trim().toLowerCase(Locale.ROOT))
            .collection("anuncios").add(anuncios).await()
        firestore.collection(departamento.trim().toLowerCase(Locale.ROOT)).document(provincia.trim().toLowerCase(Locale.ROOT))
            .collection("anuncios").document(result.id).update("id", result.id).await()
    }

    override suspend fun crearAnunciodata(departamento: String, provincia: String) {
        val datos: List<Anuncios>? = Klaxon().parseArray<Anuncios>(Constants.data)
        datos?.forEach {
            val result =
                firestore.collection(departamento.trim().toLowerCase(Locale.ROOT)).document(provincia.trim().toLowerCase(Locale.ROOT))
                    .collection("anuncios").add(it).await()
            firestore.collection(departamento.trim().toLowerCase(Locale.ROOT)).document(provincia.trim().toLowerCase(Locale.ROOT))
                .collection("anuncios").document(result.id).update("id", result.id).await()
        }
    }

    override suspend fun uploadFotoAnuncio(imagen: File) :String{
        val path = "images/${imagen.name}"
        storage.getReference(path).putFile(Uri.fromFile(imagen)).await()
        val response: Uri? =storage.getReference(path).downloadUrl.await()
        return response.toString()
    }

    override suspend fun getMisAnuncios(id: String): List<Anuncios> {
        val ubicacion = db.selectUbicacionModelStatic()

        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios").whereEqualTo("idempresa", id)
            .get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }
}