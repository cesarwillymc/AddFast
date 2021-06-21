package com.summit.core.network.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.*
import com.summit.core.exception.ExceptionGeneral
import com.summit.core.network.model.SignIn
import com.summit.core.network.model.Usuario
import com.summit.core.status.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


internal class AuthRepositoryImpl(
    private val api: AGConnectAuth,
    private val firebase: FirebaseFirestore,
    private val storage: FirebaseStorage
) : AuthRepository {


    //Enviar mensajes de texto a este numero
    override suspend fun sendNumberCode(codeCountry: String, numberPhone: String): Unit =
        suspendCancellableCoroutine { continuation ->
            val settings = VerifyCodeSettings.newBuilder()
                .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
                .sendInterval(30) // Shortest sending interval, 30–120s
                .locale(Locale.getDefault())
                .build()
            PhoneAuthProvider.requestVerifyCode(codeCountry, numberPhone, settings).addOnSuccessListener {
                continuation.resume(Unit)
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
        }

    //Verificar mensajes de texto
    override suspend fun sendVerifyCode(credential: SignIn): String? = suspendCancellableCoroutine { continuation ->
        val credentialVerify = PhoneAuthProvider.credentialWithVerifyCode(
            credential.code, credential.numberPhone, credential.password, credential.codeRecibe
        )
        api.signOut()
        api.signIn(credentialVerify).addOnSuccessListener {
            if (it.user != null)
                continuation.resume(it.user.uid)
            else
                continuation.resume(null)
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }

    }

    //Create User WITH number
    override suspend fun createUserVerifyCode(credential: SignIn): String = suspendCancellableCoroutine { continuation ->
        val phoneUser = PhoneUser.Builder()
            .setCountryCode(credential.code)
            .setPhoneNumber(credential.numberPhone)
            .setVerifyCode(credential.codeRecibe)
            .setPassword(credential.password)
            .build()

        api.createUser(phoneUser).addOnSuccessListener {
            continuation.resume(it.user.uid)
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }


    }

    //GET INFO
    override suspend fun getDataInformation(id: String): Usuario? = suspendCancellableCoroutine { continuation ->
        firebase.collection("users").document(id).get().addOnCompleteListener {
            try {
                if (it.result != null) {
                  if(it.result!!.exists()){
                      val usuario =it.result!!.toObject(Usuario::class.java)
                      continuation.resume(usuario)
                  }else{
                      continuation.resume(null)
                  }
                } else {
                    continuation.resume(null)
                }

            } catch (e: Exception) {
                continuation.resume(null)
            }
        }.addOnFailureListener {
            try {
                continuation.resumeWithException(it)
            } catch (e: Exception) {
                continuation.resumeWithException(Exception("Surgio un errorr"))
            }

        }
    }

    //CREATE DATA INFORMATION
    override suspend fun createDataInformation(id: String, usuario: Usuario): Boolean =
        suspendCancellableCoroutine { continuation ->
            firebase.collection("users").document(id).set(usuario).addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(true)
                } else {
                    continuation.resume(false)
                }
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
        }
    //Get link image storage

    override suspend fun uploadImageProfile(imagen: File) = callbackFlow<Resource<String>> {

        val path = "images/${imagen.name}"
        val storageReference = storage.getReference(path)
        //,attribute,100L
        val uploadTask = storageReference.putFile(Uri.fromFile(imagen))
        uploadTask.addOnProgressListener {
            Log.e("uploadTask", "addOnProgressListener")
            val total = ((it.bytesTransferred * 1.0) / it.totalByteCount)
            offer(Resource.loading(total))
        }.addOnSuccessListener {
            Log.e("uploadTask", "addOnSuccessListener")
            offer(Resource.success(path))
        }.addOnFailureListener {
            Log.e("uploadTask", "addOnFailureListener")
            offer(Resource.error(ExceptionGeneral(it.message ?: "")))
        }
        awaitClose {
            uploadTask.cancel()
        }
    }


}