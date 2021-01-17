package com.summit.android.addfast.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.huawei.agconnect.auth.PhoneAuthProvider
import com.huawei.agconnect.auth.PhoneUser
import com.huawei.agconnect.auth.VerifyCodeSettings
import com.summit.android.addfast.repo.conexion.AuthRepository
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.utils.lifeData.RsrProgress
import kotlinx.coroutines.flow.collect
import java.io.File
import java.util.*


class AuthViewModel(private val repo: AuthRepository) : ViewModel() {


    //USER DB
    fun getDataDBstatic() = repo.getUserStatic()

    fun getDataDB() = repo.getUserTimeReal()


    //Sign In Logged
    fun loggedInvitado() = liveData<RsrProgress<Unit>> {
        emit(RsrProgress.loading(0.0))
        try {
            repo.signInAnonimous()
            repo.insertUser(
                Usuario(
                    "Invitado", "aa ", "Peru", "Puno",
                    "https://summit-puno.s3.us-east-2.amazonaws.com/yachay/boy.webp",_id = "invitado"
                )
            )
            emit(RsrProgress.success(Unit))
        } catch (e: Exception) {
            emit(RsrProgress.error(e))
        }

    }

    //Enviar mensajes de texto
    fun sendMessageLogged(code: String, numberPhone: String) = liveData<RsrProgress<Boolean>> {
        emit(RsrProgress.loading(0.0))
        try {
            val settings = VerifyCodeSettings.newBuilder()
                .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
                .sendInterval(30) // Shortest sending interval, 30–120s
                .locale(Locale.getDefault())
                .build()
            repo.sendNumberCode(settings, code, numberPhone)
            emit(RsrProgress.success(false))
        } catch (e: Exception) {
            if(e.message!!.contains("")){
                emit(RsrProgress.error(Exception("Se enviaron muchas veces codigo con este numero, espera hasta mañana")))
            }else{
                emit(RsrProgress.error(e))
            }

        }
    }

    //Verificar y validad cofigo
    fun verifyMessageLogged(code: String, numberPhone: String, codeRecibe: String) =
        liveData<RsrProgress<String?>> {
            emit(RsrProgress.loading(0.0))
            try {
                //Inicio de sesion

                //Verifica el codigo que trajo
                val credential = PhoneAuthProvider.credentialWithVerifyCode(
                    code, numberPhone,"th3lastford4ad", codeRecibe
                )

                var response = repo.sendVerifyCode(credential)
                if (response==null){
                    Log.e("TAG","Response null")
                    val phoneUser = PhoneUser.Builder()
                        .setCountryCode(code)
                        .setPhoneNumber(numberPhone)
                        .setVerifyCode(codeRecibe)
                        .setPassword("th3lastford4ad")
                        .build()
                    response= repo.createUserVerifyCode(phoneUser)
                }
                //Trae datos de firebase para comprobar si era nuevo usuario
                val traerDatos = repo.getDataInformation(response.user.uid)

                Log.e("login","function ${traerDatos}")
                //Inserta el usuario en caso exista y en caso no se ira a la siguiente ventana de registro
                if (traerDatos!=null){
                    if(traerDatos.accountactivate){
                        if(traerDatos.reportes<=10){
                            repo.insertUser(traerDatos!!)
                            emit(RsrProgress.success(null))
                        }else{
                            repo.disableAccount(traerDatos._id)
                            emit(RsrProgress.error(Exception("Tu cuenta fue reportada muchas veces")))
                        }
                    }else{
                        emit(RsrProgress.error(Exception("Tu cuenta fue desactivada por incumplir nuestras normas")))
                    }

                }else{
                    emit(RsrProgress.success(response.user.uid))
                }

            } catch (e: Exception) {
                emit(RsrProgress.error(e))
            }
        }

    //Create fotoProfile User
    fun crearFotoProfileUser(imagen: File) = liveData<RsrProgress<String>> {
        emit(RsrProgress.loading(0.0))
        try {
            repo.uploadImageProfile(imagen).collect {
                emit(it)
            }
        } catch (e: Exception) {
            emit(RsrProgress.error(e))
        }
    }


    //Crear Usuario en Firebase
    fun createDataUser(usuario: Usuario) = liveData<RsrProgress<Unit>> {
        emit(RsrProgress.loading(0.0))
        try {
            val getEnlaceUrl= repo.getUrlDownloadFile(usuario.uriImgPerfil!!)
            usuario.uriImgPerfil = getEnlaceUrl
            usuario.admin=false
            val data = repo.createDataInformation(usuario._id, usuario)
            if (data){
                repo.insertUser(usuario)
            }else{
                emit(RsrProgress.error(Exception("Sucedio un problema, vuelva a intentarlo mas tarde.")))
            }
        } catch (e: Exception) {
            emit(RsrProgress.error(e))
        }

    }
}