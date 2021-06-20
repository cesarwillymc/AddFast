package com.summit.core.network.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import com.summit.core.db.AppDB
import com.summit.core.db.dao.UsuarioDao
import com.summit.core.network.model.Reporte
import com.summit.core.network.model.Usuario
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class UserRepositoryImpl(
    private val db: UsuarioDao, private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage, private val api: AGConnectAuth,
) : UserRepository {
    override suspend fun getUrlDownloadFile(path: String): String = suspendCancellableCoroutine { continuation ->
        storage.getReference(path).downloadUrl.addOnSuccessListener {
            continuation.resume(it.toString())
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }

    override fun insertUser(usuario: Usuario) = db.insertUsuario(usuario)

    override fun updateUser(usuario: Usuario) = db.updateUsuario(usuario)

    override fun deleteUser() {
        db.deleteUsuario()
        api.signOut()
        api.deleteUser()
    }

    override fun getUserStatic() = db.selectUsuarioStatic()

    override fun getUserTimeReal() = db.selectUsuario()

    override suspend fun disableAccount(id: String) {
        val result = firestore.collection("users").document(id).update("accountactivate", false).await()
        return Unit
    }

    override  suspend fun activeAccount(id: String) {
        val result = firestore.collection("users").document(id).update("accountactivate", true).await()
        return Unit
    }

    override suspend fun disableAdmin(id: String) {
        val result = firestore.collection("users").document(id).update("isAdmin", false).await()
        return Unit
    }

    override suspend fun addAdmin(id: String) {
        val result = firestore.collection("users").document(id).update("isAdmin", true).await()
        return Unit
    }
    override suspend fun crearReporte(reporte: Reporte) {
        firestore.collection("reporte").add(reporte).await()
        return Unit
    }

    override suspend fun reportarUsuario(iduser: String) {
        firestore.collection("users").document(iduser).update("reportes", FieldValue.increment(1)).await()
        return Unit
    }
}