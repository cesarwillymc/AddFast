package com.summit.core.network.repository

import android.net.Uri
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.AppDB
import com.summit.core.exception.ExceptionGeneral
import com.summit.core.network.model.Postulacion
import com.summit.core.network.model.Usuario
import com.summit.core.status.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.File

internal class PostulateRepositoryImpl(
    private val db: AppDB, private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : PostulateRepository {
    override suspend fun getAllPostulante(): List<Usuario> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection("users").get().await()
        return anuncios.toObjects(Usuario::class.java)
    }

    override suspend fun getAllPostulanteByPalabra(palabra: String): List<Usuario> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection("users").whereArrayContains("titulo", palabra).get().await()
        return anuncios.toObjects(Usuario::class.java)
    }

    override suspend fun postularAnuncio(id: String, idPostulacion: String) {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").document(id)
            .update("postulaciones", FieldValue.arrayUnion(idPostulacion)).await()
        return Unit
    }

    override suspend fun uploadCurriculumPostulacion(cv: File) = callbackFlow<Resource<String>> {
        val path = "curriculum/${cv.name}"
        val storageReference = storage.getReference(path)
        val uploadTask = storageReference.putFile(Uri.fromFile(cv))
        uploadTask.addOnProgressListener {
            val total = ((it.bytesTransferred * 1.0) / cv.length())
            val total2 = ((it.bytesTransferred * 1.0) / it.totalByteCount)
            //  Log.e("SubiendoDatos","total $total 2 $total2 ${it.bytesTransferred} ${it.totalByteCount} ${cv.length()}  ")
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

    override suspend fun crearPostulacion(postulacion: Postulacion): String {
        val data = firestore.collection("postulaciones").add(postulacion).await()
        firestore.collection("postulaciones").document(data.id).update("id", data.id).await()
        return data.id
    }

    override suspend fun cambiarEstadoPostulacion(id: String, message: String): Unit {
        firestore.collection("postulaciones").document(id).update("estado", message).await()
        return Unit
    }

    override suspend fun verMisPostulaciones(id: String): List<Postulacion> {
        val anuncios = firestore.collection("postulaciones").whereEqualTo("idpostulante", id).get().await()
        return anuncios.toObjects(Postulacion::class.java)
    }

    override suspend fun verPostulacionesdemiAnuncio(idAnuncio: String): List<Postulacion> {
        val anuncios = firestore.collection("postulaciones").whereEqualTo("idanuncio", idAnuncio).get().await()
        return anuncios.toObjects(Postulacion::class.java)
    }
}