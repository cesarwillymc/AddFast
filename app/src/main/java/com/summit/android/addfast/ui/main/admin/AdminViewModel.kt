package com.summit.android.addfast.ui.main.admin

import android.util.Log
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.summit.android.addfast.repo.conexion.AdminRepository
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.repo.model.Promociones
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.utils.dataStore
import com.summit.android.addfast.utils.lifeData.RsrProgress
import kotlinx.coroutines.flow.collect
import java.io.File

class AdminViewModel(private val repo: AdminRepository) :ViewModel(){

    fun  getAllAnuncios() = liveData<RsrProgress<List<Anuncios>>> {

        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.getAllAnuncios()
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  getAllAnunciosPost() = liveData<RsrProgress<List<Anuncios>>> {

        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.getAllAnunciosPost()
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  getAllAnunciosByPalabra(palabra: String) = liveData<RsrProgress<List<Anuncios>>> {

        emit(RsrProgress.loading(0.0))
        try {
            Log.e("palabra",palabra)
            val resultado=repo.getAllAnunciosByPalabra(palabra)
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  getAllPostulantes() = liveData<RsrProgress<List<Usuario>>> {

        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.getAllPostulante()
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  getAllPostulantesByPalabra(palabra: String) = liveData<RsrProgress<List<Usuario>>> {

        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.getAllPostulanteByPalabra(palabra)
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  cambiarEstado(id:String,palabra: String) = liveData<RsrProgress<Unit>> {

        emit(RsrProgress.loading(0.0))
        try {
            repo.cambiarEstadoAnuncio(id,palabra)
            emit( RsrProgress.success(Unit))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }


    fun  disableAccount(id:String) = liveData<RsrProgress<Unit>> {

        emit(RsrProgress.loading(0.0))
        try {
            repo.disableAccount(id)
            emit( RsrProgress.success(Unit))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  activeAccount(id:String) = liveData<RsrProgress<Unit>> {

        emit(RsrProgress.loading(0.0))
        try {
            repo.activeAccount(id)
            emit( RsrProgress.success(Unit))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  addAdmin(id:String) = liveData<RsrProgress<Unit>> {

        emit(RsrProgress.loading(0.0))
        try {
            repo.addAdmin(id)
            emit( RsrProgress.success(Unit))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  disableAdmin(id:String) = liveData<RsrProgress<Unit>> {

        emit(RsrProgress.loading(0.0))
        try {
            repo.disableAdmin(id)
            emit( RsrProgress.success(Unit))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }

    //Promociones
    fun  getAllPromociones() = liveData<RsrProgress<List<Promociones>>> {

        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.getAllPromociones()
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  desactivarPromocion(id:String) = liveData<RsrProgress<Unit>> {

        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.desactivarPromocion(id)
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  subirImagenPromocion(imagen: File) = liveData<RsrProgress<String>> {
        emit(RsrProgress.loading(0.0))
        try {
            repo.uploadFotoPromocion(imagen).collect {
                emit(it)
            }
        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  crearPromocion(anuncios: Promociones,pathanuncio:String) = liveData<RsrProgress<Int>> {
        emit(RsrProgress.loading(0.0))
        try {
            val urlanuncio= repo.getUrlDownloadFile(pathanuncio)
            anuncios.img=urlanuncio
            repo.crearPromocion(anuncios)
            emit(RsrProgress.success(0))
        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }

    val getUserData = repo.getUser()
    fun deleteUser()= repo.deleteUser()
    fun getStaticDataUser()= repo.getUserStatic()
    fun saveUbicacion(item: UbicacionModel) =repo.saveUbicacion(item)
    fun getUbicacion() =repo.getUbicacion()
    private val permission = preferencesKey<Boolean>("permissionComplete")
    val getDataPermission = liveData {
        try{
            dataStore.data.collect {
                emit(  it[permission]?:false)
            }
        }catch (e:Exception){
            emit(false)
        }
    }
}