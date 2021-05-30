package com.summit.android.addfast.repo.conexion

import android.net.Uri
import com.beust.klaxon.Klaxon
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import com.summit.android.addfast.repo.local.AppDB
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import com.summit.android.addfast.repo.model.*
import com.summit.android.addfast.repo.model.departamento.ProvinciaItem
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.utils.Constants
import com.summit.android.addfast.utils.lifeData.RsrProgress
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AdminRepository(
        private val db: AppDB,
        private val hms: AGConnectAuth,
        private val firestore: FirebaseFirestore,
        private val storage: FirebaseStorage
) {


    /**Usuario**/
    fun deleteUser() = db.usuarioDao.deleteUsuario()
    fun saveUser(perfilUsuario: Usuario) = db.usuarioDao.insertUsuario(perfilUsuario)
    fun updateUserAppDb(perfilUsuario: Usuario) = db.usuarioDao.updateUsuario(perfilUsuario)
    fun getUser() = db.usuarioDao.selectUsuario()
    fun getUserStatic() = db.usuarioDao.selectUsuarioStatic()
    /**UBICACION**/
    fun deleteUbicacion() = db.ubicacionModelDao.deleteUbicacionModel()
    fun saveUbicacion(perfilUsuario: UbicacionModel) = db.ubicacionModelDao.insertUbicacionModel(perfilUsuario)
    fun updateUbicacionAppDb(perfilUsuario: UbicacionModel) = db.ubicacionModelDao.updateUbicacionModel(perfilUsuario)
    fun getUbicacion() = db.ubicacionModelDao.selectUbicacionModel()
    fun getUbicacionStatic() = db.ubicacionModelDao.selectUbicacionModel()


    /**Anuncios**/
    //PUBLICADO
    //FINALIZADO
    suspend fun cambiarEstadoAnuncio(id: String,message:String): Unit {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
         firestore.collection(ubicacion.departamento.trim().toLowerCase(Locale.ROOT)).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").document(id).update("estado",message).await()
        return Unit
    }
    suspend fun disableAccount( id:String){
        val result=firestore.collection("users").document(id).update("accountactivate",false).await()
        return Unit
    }
    suspend fun activeAccount( id:String){
        val result=firestore.collection("users").document(id).update("accountactivate",true).await()
        return Unit
    }
    suspend fun disableAdmin( id:String){
        val result=firestore.collection("users").document(id).update("isAdmin",false).await()
        return Unit
    }
    suspend fun addAdmin( id:String){
        val result=firestore.collection("users").document(id).update("isAdmin",true).await()
        return Unit
    }

    suspend fun getAllAnunciosByPalabra(palabra: String): List<Anuncios> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").whereEqualTo("estado", "PENDIENTE").get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }
    suspend fun getAllAnuncios(): List<Anuncios> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").whereEqualTo("estado", "PENDIENTE").get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }
    suspend fun getAllAnunciosPost(): List<Anuncios> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").whereEqualTo("estado", "PUBLICADO").get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }

    suspend fun getAllPostulanteByPalabra(palabra: String): List<Usuario> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection("users").whereArrayContains("titulo",palabra).get().await()
        return anuncios.toObjects(Usuario::class.java)
    }
    suspend fun getAllPostulante(): List<Usuario> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection("users").get().await()
        return anuncios.toObjects(Usuario::class.java)
    }
    //promociones
    suspend fun getAllPromociones(): List<Promociones> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("promocion").whereEqualTo("state", true).get().await()
        return anuncios.toObjects(Promociones::class.java)
    }
    suspend fun getUrlDownloadFile(path: String): String = suspendCancellableCoroutine { continuation ->
        storage.getReference(path).downloadUrl.addOnSuccessListener {
            continuation.resume(it.toString())
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }
    suspend fun uploadFotoPromocion(imagen: File) =callbackFlow<RsrProgress<String>> {
        val path = "images/${imagen.name}"
        val storageReference = storage.getReference(path)
        //,attribute,100L
        val uploadTask = storageReference.putFile(Uri.fromFile(imagen))
        uploadTask.addOnProgressListener {
            val total = ((it.bytesTransferred * 1.0) / imagen.length())
            offer(RsrProgress.loading(total))
        }.addOnSuccessListener {
            offer(RsrProgress.success(path))
        }.addOnFailureListener {
            offer(RsrProgress.error(it))
        }
        awaitClose {
            uploadTask.cancel()
        }
    }
    suspend fun crearPromocion(model:Promociones): Unit {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("promocion").add(model).await()
        firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("promocion").document(anuncios.id).update("id",anuncios.id).await()
        return Unit
    }
    suspend fun desactivarPromocion(id:String): Unit {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("promocion").document(id).update("state",false).await()
        return Unit
    }



    //Departamerto
    suspend fun verDepartamento(): List<ProvinciaItem>? {
        return Klaxon().parseArray<ProvinciaItem>(Constants.departamentoJson)
    }

    suspend fun verProvincia(id: String): List<ProvinciaItem>? {
        val datos = mutableListOf<ProvinciaItem>()
        Klaxon().parseArray<ProvinciaItem>(Constants.provinciaJson)?.forEach {
            if (it.department_id == id) {
                datos.add(it)
            }
        }
        return datos
    }
}