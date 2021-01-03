package com.summit.android.addfast.repo.conexion

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.huawei.agconnect.auth.*
import com.summit.android.addfast.repo.local.AppDB
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.utils.lifeData.RsrProgress
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class AuthRepository(
        private val db: AppDB,
        private val api: AGConnectAuth,
        private val firebase: FirebaseFirestore,
        private val storage:FirebaseStorage
){
    //SIGN IN DATA
    suspend fun signInAnonimous():SignInResult = suspendCancellableCoroutine{ continuation->
        api.signInAnonymously().addOnSuccessListener {
            continuation.resume(it)
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }

    //Enviar mensajes de texto a este numero
    suspend fun sendNumberCode(setting: VerifyCodeSettings, codeCountry: String, numberPhone: String):Unit =  suspendCancellableCoroutine { continuation->
        PhoneAuthProvider.requestVerifyCode(codeCountry, numberPhone, setting).addOnSuccessListener {
            continuation.resume(Unit)
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }
    //Verificar mensajes de texto
    suspend fun sendVerifyCode(credential: AGConnectAuthCredential):SignInResult? =  suspendCancellableCoroutine { continuation->
        api.signIn(credential).addOnSuccessListener {
            Log.e("TAG", "sendVerifyCode ${it.user.uid}")
            if (it.user!=null)
                continuation.resume(it)
            else
                continuation.resume(null)
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }

    }
    //Create User WITH number
    suspend fun createUserVerifyCode(credential: PhoneUser):SignInResult =  suspendCancellableCoroutine { continuation->
        api.createUser(credential).addOnSuccessListener {
            continuation.resume(it)
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }

    }
    //GET INFO
    suspend fun getDataInformation(id: String): Usuario? = suspendCancellableCoroutine { continuation->
        firebase.collection("users").document(id).get().addOnCompleteListener {
            if (it.result!!.exists()){
                continuation.resume(it.result!!.toObject(Usuario::class.java))
            }else{
                continuation.resume(null)
            }
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }
    //CREATE DATA INFORMATION
    suspend fun createDataInformation(id: String, usuario: Usuario): Boolean = suspendCancellableCoroutine { continuation->
        firebase.collection("users").document(id).set(usuario).addOnCompleteListener {
            if (it.isSuccessful){
                continuation.resume(true)
            }else{
                continuation.resume(false)
            }
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }
    //Get link image storage
    suspend fun getUrlDownloadFile(path:String): String = suspendCancellableCoroutine  {continuation->
        storage.getReference(path).downloadUrl.addOnSuccessListener {
            continuation.resume(it.toString())
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }
    suspend fun uploadImageProfile(imagen: File): Flow<RsrProgress<String>> = callbackFlow{

        val path="images/${imagen.name}"
        val storageReference = storage.getReference(path)
        //,attribute,100L
        val uploadTask = storageReference.putFile(Uri.fromFile(imagen))
        uploadTask.addOnProgressListener {
            Log.e("uploadTask","addOnProgressListener")
            val total = ((it.bytesTransferred*1.0) /it.totalByteCount )
            offer(RsrProgress.loading(total))
        }.addOnSuccessListener {
            Log.e("uploadTask","addOnSuccessListener")
            offer(RsrProgress.success(path))
        }.addOnFailureListener {
            Log.e("uploadTask","addOnFailureListener")
            offer(RsrProgress.error(it))
        }
        awaitClose {
            uploadTask.cancel()
        }
    }

    //DISABLE ACCOUND
    suspend fun disableAccount( id:String){
        val result=firebase.collection("users").document(id).update("accountactivate",false).await()
        return Unit
    }
    //CRUD BASICO DB BASE INSERT, DELETE, UPDATE, SELECT

    fun insertUser(usuario: Usuario) = db.usuarioDao.insertUsuario(usuario)

    fun updateUser(usuario: Usuario) = db.usuarioDao.updateUsuario(usuario)

    fun deleteUser() = db.usuarioDao.deleteUsuario()

    fun getUserStatic() = db.usuarioDao.selectUsuarioStatic()

    fun getUserTimeReal() = db.usuarioDao.selectUsuario()

}