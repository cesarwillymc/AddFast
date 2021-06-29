package com.summit.core.network.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import com.summit.core.db.dao.UsuarioDao
import com.summit.core.network.model.Usuario
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class UserRepositoryImpl(
    private val db: UsuarioDao,
    private val storage: FirebaseStorage, private val api: AGConnectAuth,
) : UserRepository {
    override suspend fun getUrlDownloadFile(path: String): String = suspendCancellableCoroutine { continuation ->
        storage.getReference(path).downloadUrl.addOnSuccessListener {
            continuation.resume(it.toString())
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }

    override suspend fun insertUser(usuario: Usuario) {
        db.insertUsuario(usuario)
    }

    override fun updateUser(usuario: Usuario) = db.updateUsuario(usuario)

    override fun deleteUser() {
        db.deleteUsuario()
        api.signOut()
        api.deleteUser()
    }

    override fun getUserStatic() = db.selectUsuarioStatic()

    override fun getUserTimeReal() = db.selectUsuario()


}