package com.summit.core.network.repository

import android.net.Uri
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.dao.UbicacionModelDao
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
        val data = firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios").document(id).get()
            .await()

        return data.toObject(Anuncios::class.java)!!
    }


    override suspend fun aumentarVisualizacionesAnuncios(id: String){
        val ubicacion = db.selectUbicacionModelStatic()

        firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios").document(id)
            .update("visualizaciones",FieldValue.increment(1) ).await()
        return
    }


    override suspend fun crearAnuncio(anuncios: Anuncios, departamento: String, provincia: String) {
        val result = firestore.collection(departamento.trim().toLowerCase(Locale.ROOT))
            .document(provincia.trim().toLowerCase(Locale.ROOT))
            .collection("anuncios").add(anuncios).await()
        firestore.collection(departamento.trim().toLowerCase(Locale.ROOT))
            .document(provincia.trim().toLowerCase(Locale.ROOT))
            .collection("anuncios").document(result.id).update("id", result.id).await()
        return
    }


    override suspend fun uploadFotoAnuncio(imagen: File): String {
        val path = "images/${imagen.name}"
        storage.getReference(path).putFile(Uri.fromFile(imagen)).await()
        val response: Uri? = storage.getReference(path).downloadUrl.await()
        return response.toString()
    }

    override suspend fun getMisAnuncios(id: String): List<Anuncios> {
        val ubicacion = db.selectUbicacionModelStatic()

        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios")
            .whereEqualTo("idempresa", id)
            .get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }
}