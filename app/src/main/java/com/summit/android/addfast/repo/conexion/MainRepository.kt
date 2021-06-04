/*
package com.summit.android.addfast.repo.conexion

import android.net.Uri
import android.util.Log
import com.beust.klaxon.Klaxon
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import com.summit.android.addfast.repo.local.AppDB
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import com.summit.android.addfast.repo.model.*
import com.summit.android.addfast.repo.model.departamento.ProvinciaItem
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.utils.Constants
import com.summit.android.addfast.utils.lifeData.RsrProgress
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MainRepository(
        private val db: AppDB,
        private val hms: AGConnectAuth,
        private val firestore: FirebaseFirestore,
        private val storage: FirebaseStorage
) {
    suspend fun getUrlDownloadFile(path: String): String = suspendCancellableCoroutine { continuation ->
        storage.getReference(path).downloadUrl.addOnSuccessListener {
            continuation.resume(it.toString())
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }

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
    /**Categorias**/
    suspend fun getAllCategorias(): List<CategoriasModel> {
        val categorias = firestore.collection("categorias").get().await()
        return categorias.toObjects(CategoriasModel::class.java)
    }

    /**Anuncios**/
    suspend fun cambiarEstadoAnuncio(id: String,message:String): Unit {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
         firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").document(id).update("estado",message).await()
        return Unit
    }
    suspend fun getAnunciosByCategorias(id: String): List<Anuncios> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        Log.e("getAnunciosByCategorias","$id $${ubicacion.departamento.trim().toLowerCase()}  ${ubicacion.provincia.trim().toLowerCase()}" )
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").whereEqualTo("typeID", id).whereEqualTo("estado", "PUBLICADO").limit(5).get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }

    suspend fun getAllAnunciosByCategorias(id: String): List<Anuncios> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").whereEqualTo("typeID", id).whereEqualTo("estado", "PUBLICADO").get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }

    suspend fun aumentarVisualizacionesAnuncios(id: String) {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").document(id).update("visualizaciones", FieldValue.increment(1)).await()
        return Unit
    }

    suspend fun reportarAnuncio(id: String) {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").document(id).update("reporte", FieldValue.increment(1)).await()
        return Unit
    }
    suspend fun crearReporte(reporte: Reporte) {
        firestore.collection("reporte").add(reporte).await()
        return Unit
    }

    suspend fun reportarUsuario(iduser: String) {
        firestore.collection("users").document(iduser).update("reportes", FieldValue.increment(1)).await()
        return Unit
    }

    suspend fun crearAnuncio(anuncios: Anuncios, departamento: String, provincia: String) {
        val result=firestore.collection(departamento.trim().toLowerCase()).document(provincia.trim().toLowerCase()).collection("anuncios").add(anuncios).await()
        firestore.collection(departamento.trim().toLowerCase()).document(provincia.trim().toLowerCase()).collection("anuncios").document(result.id).update("id",result.id).await()
        return Unit
    }
    suspend fun crearAnunciodata( departamento: String, provincia: String) {
        val datos: List<Anuncios>? = Klaxon().parseArray<Anuncios>(Constants.data)
        datos?.forEach{
            val result=firestore.collection(departamento.trim().toLowerCase()).document(provincia.trim().toLowerCase()).collection("anuncios").add(it).await()
            firestore.collection(departamento.trim().toLowerCase()).document(provincia.trim().toLowerCase()).collection("anuncios").document(result.id).update("id",result.id).await()
        }

        return Unit
    }

    suspend fun uploadFotoAnuncio(imagen: File) = callbackFlow<RsrProgress<String>>  {
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

    suspend fun getMisAnuncios(id: String): List<Anuncios> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        Log.e("getMisAnuncios","${ubicacion.departamento.trim().toLowerCase()}   ${ubicacion.provincia.trim().toLowerCase()}")
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").whereEqualTo("idempresa", id).get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }


    /**promocion**/
    suspend fun getPromocion(): List<Promociones> {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("promocion").whereEqualTo("state",true).get().await()
        return anuncios.toObjects(Promociones::class.java)
    }
    suspend fun getAnuncioId(id: String): Anuncios {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        val dato =firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").document(id).get().await()
        return dato.toObject(Anuncios::class.java)!!
    }

    /**Postulaciones**/

    suspend fun postularAnuncio(id: String, idPostulacion: String) {
        val ubicacion = db.ubicacionModelDao.selectUbicacionModelStatic()
        firestore.collection(ubicacion.departamento.trim().toLowerCase()).document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").document(id).update("postulaciones", FieldValue.arrayUnion(idPostulacion)).await()
        return Unit
    }

    suspend fun uploadCurriculumPostulacion(cv: File) = callbackFlow<RsrProgress<String>> {
        val path = "curriculum/${cv.name}"
        val storageReference = storage.getReference(path)
        val uploadTask = storageReference.putFile(Uri.fromFile(cv))
        uploadTask.addOnProgressListener {
            val total = ((it.bytesTransferred * 1.0) / cv.length())
            val total2 = ((it.bytesTransferred * 1.0) /it.totalByteCount)
          //  Log.e("SubiendoDatos","total $total 2 $total2 ${it.bytesTransferred} ${it.totalByteCount} ${cv.length()}  ")
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

    suspend fun crearPostulacion(postulacion: Postulacion): String {
        val data = firestore.collection("postulaciones").add(postulacion).await()
        firestore.collection("postulaciones").document(data.id).update("id",data.id).await()
        return data.id
    }
    suspend fun cambiarEstadoPostulacion(id: String,message:String): Unit {
        firestore.collection("postulaciones").document(id).update("estado",message).await()
        return Unit
    }

    suspend fun verMisPostulaciones(id: String): List<Postulacion> {
        val anuncios = firestore.collection("postulaciones").whereEqualTo("idpostulante", id).get().await()
        return anuncios.toObjects(Postulacion::class.java)
    }

    suspend fun verPostulacionesdemiAnuncio(idAnuncio: String): List<Postulacion> {
        val anuncios = firestore.collection("postulaciones").whereEqualTo("idanuncio", idAnuncio).get().await()
        return anuncios.toObjects(Postulacion::class.java)
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
 */