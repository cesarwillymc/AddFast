package com.summit.core.network.repository

import android.net.Uri
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.network.model.Postulacion
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.*

internal class PostulateRepositoryImpl(
    private val db: UbicacionModelDao, private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : PostulateRepository {



    override suspend fun postularAnuncio(id: String, idPostulacion: String) {
        val ubicacion = db.selectUbicacionModelStatic()
        firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT))
            .document(ubicacion.provincia.trim().toLowerCase(Locale.ROOT)).collection("anuncios").document(id)
            .update("postulaciones", FieldValue.arrayUnion(idPostulacion)).await()

        return
    }

    override suspend fun uploadCurriculumPostulacion(cv: File): String {
        val path = "curriculum/${cv.name}"
        val storageReference = storage.getReference(path)
        storageReference.putFile(Uri.fromFile(cv)).await()
        val response: Uri? =storage.getReference(path).downloadUrl.await()
        return response.toString()
    }

    override suspend fun crearPostulacion(postulacion: Postulacion): String {
        val data = firestore.collection("postulaciones").add(postulacion).await().id
        firestore.collection("postulaciones").document(data).update("id", data).await()
        return data
    }



    override suspend fun verMisPostulaciones(id: String): List<Postulacion> {
        val anuncios = firestore.collection("postulaciones").whereEqualTo("idpostulante", id).get().await()
        return anuncios.toObjects(Postulacion::class.java)
    }

}