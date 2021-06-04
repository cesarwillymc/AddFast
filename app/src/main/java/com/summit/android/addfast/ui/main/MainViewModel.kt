/*
package com.summit.android.addfast.ui.main

import android.util.Log
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.summit.android.addfast.repo.conexion.MainRepository
import com.summit.android.addfast.repo.model.*
import com.summit.android.addfast.repo.model.departamento.ProvinciaItem
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.utils.dataStore
import com.summit.android.addfast.utils.lifeData.RsrProgress
import kotlinx.coroutines.flow.collect
import java.io.File
import java.util.*

class MainViewModel(private val repo: MainRepository) :ViewModel(){
    //Permission data

    private val permission = preferencesKey<Boolean>("permissionComplete")
    fun updateUser(item: Usuario) =repo.updateUserAppDb(item)

    val getDataPermission = liveData {
        try{

            dataStore.data.collect {
                emit(  it[permission]?:false)
            }
        }catch (e:Exception){
            emit(false)
        }
    }
    val anuncioCreate= MutableLiveData<Anuncios>()
    /** UBICACION UPDATE **/
    fun updateUbicacionAppDb(item: UbicacionModel) =repo.updateUbicacionAppDb(item)
    fun saveUbicacion(item: UbicacionModel) =repo.saveUbicacion(item)
    fun getUbicacion() =repo.getUbicacion()

    /** PROMOCIONES **/
    val promocionesData= MutableLiveData<List<Promociones>>(listOf())
    fun  getPromocionesUpdate() = liveData<RsrProgress<List<Promociones>>> {
       emit( RsrProgress.loading(0.0))
        try {
            val response = repo.getPromocion()
            if(response.isNotEmpty()){
                promocionesData.postValue(response)
            }else{
                promocionesData.postValue(listOf())
            }
            emit(RsrProgress.success(response))
        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  getAnuncioId(id: String) = liveData<RsrProgress<Anuncios>> {
       emit( RsrProgress.loading(0.0))
        try {
            val response = repo.getAnuncioId(id)
            emit(RsrProgress.success(response))
        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    /** ANUNCIOS **/
    fun  cambiarEstadoAnuncio(id: String,message:String) = liveData<RsrProgress<Unit>> {

        emit(RsrProgress.loading(0.0))
        try {
            repo.cambiarEstadoAnuncio(id,message)
            emit( RsrProgress.success(Unit))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  reportarAnuncio(anuncios: Anuncios,reporte:String) = liveData<RsrProgress<Int>> {

        emit(RsrProgress.loading(0.0))
        try {
           if ( anuncios.reporte>6){
               repo.reportarUsuario(anuncios.idempresa)
           }
            repo.reportarAnuncio(anuncios.id)
            val iduser=repo.getUserStatic()._id
            repo.crearReporte(Reporte(anuncios.id,anuncios.idempresa,iduser,reporte,anuncios.titulo, Date().time))
            emit( RsrProgress.success(0))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  subirImagenAnuncio(imagen:File) = liveData<RsrProgress<String>> {
        emit(RsrProgress.loading(0.0))
        try {
            repo.uploadFotoAnuncio(imagen).collect {
                emit(it)
            }
        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  crearAnuncio(anuncios: Anuncios,pathanuncio:String,departamento:String,provincia:String) = liveData<RsrProgress<Int>> {
        emit(RsrProgress.loading(0.0))
        try {
            val urlanuncio= repo.getUrlDownloadFile(pathanuncio)
            anuncios.img=urlanuncio
            repo.crearAnuncio(anuncios,departamento,provincia)
            emit(RsrProgress.success(0))
        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  crearAnunciodata(departamento:String,provincia:String) = liveData<RsrProgress<Int>> {
        emit(RsrProgress.loading(0.0))
        try {
            repo.crearAnunciodata(departamento,provincia)
            emit(RsrProgress.success(0))
        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  aumentarVisualizacionesAnuncios(anuncios: Anuncios) = liveData<RsrProgress<Int>> {

        emit(RsrProgress.loading(0.0))
        try {
            repo.aumentarVisualizacionesAnuncios(anuncios.id)
            emit( RsrProgress.success(0))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  getAllAnunciosByCategorias(id: String) = liveData<RsrProgress<List<Anuncios>>> {

        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.getAllAnunciosByCategorias(id)
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    val anunciosData= MutableLiveData<List<ListaAnuncios>>(listOf())
    fun  getAnunciosByCategorias() = liveData<RsrProgress<List<ListaAnuncios>>> {
        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.getAllCategorias()
            val listado: MutableList<ListaAnuncios> = mutableListOf()
            var dato=0
            resultado.forEach {

                val waiting=repo.getAnunciosByCategorias(it.id)
                if(waiting.isNotEmpty()){
                    listado.add(ListaAnuncios(it.id,it.name,waiting))
                }
                if(dato==resultado.size-1){
                    if (listado.isNotEmpty()){
                        anunciosData.postValue(listado)
                    }

                }
                dato++

            }

            emit( RsrProgress.success(listado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  getCategorias() = liveData<RsrProgress<List<CategoriasModel>>> {
        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.getAllCategorias()
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  getMisAnuncios(idUser: String) = liveData<RsrProgress<List<Anuncios>>> {

        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.getMisAnuncios(idUser)
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    /** POSTULALCIONES**/
    fun  subirCurriculumPostulacion(curriculum:File) = liveData<RsrProgress<String>> {
        emit(RsrProgress.loading(0.0))
        try {
            repo.uploadCurriculumPostulacion(curriculum).collect {
                emit(it)
            }
        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  crearPostulacion(postulacion: Postulacion,pathcv:String) = liveData<RsrProgress<String>> {

        emit(RsrProgress.loading(0.0))
        try {
            val urlpdf= repo.getUrlDownloadFile(pathcv)
            postulacion.archivopdf=urlpdf
            val resultado=repo.crearPostulacion(postulacion)
            repo.postularAnuncio(postulacion.idanuncio,resultado)
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  cambiarEstadoPostulacion(id: String,message:String) = liveData<RsrProgress<Unit>> {

        emit(RsrProgress.loading(0.0))
        try {
            repo.cambiarEstadoPostulacion(id,message)
            emit( RsrProgress.success(Unit))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }

    fun  verMisPostulaciones(idUser: String) = liveData<RsrProgress<List<Postulacion>>> {

        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.verMisPostulaciones(idUser)
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  verPostulacionesdemiAnuncio(idAnuncio: String) = liveData<RsrProgress<List<Postulacion>>> {

        emit(RsrProgress.loading(0.0))
        try {
            val resultado=repo.verPostulacionesdemiAnuncio(idAnuncio)
            emit( RsrProgress.success(resultado))

        }catch (e:Exception){
            emit(RsrProgress.error(e))
        }
    }
    fun  getDepartamentos() = liveData<List<ProvinciaItem>> {

        try {
            val resultado=repo.verDepartamento()
            if(resultado!=null){
                emit(resultado)
            }else{
                emit(listOf())
            }

        }catch (e:Exception){
            Log.e("getProvincias",e.message!!)
        }
    }
    fun  getProvincias(id: String) = liveData<List<ProvinciaItem>> {
        try {
            val resultado=repo.verProvincia(id)
            if(resultado!=null){
                emit(resultado)
            }else{
                emit(listOf())
            }

        }catch (e:Exception){
            Log.e("getProvincias",e.message!!)
        }
    }
    //    fun getListHistory(fecha:String):LiveData<Resource<List<historyitem>>?> = liveData(Dispatchers.Main){
//        emit(Resource.Loading())
//        try {
//            Log.e("datos"," getListHistory")
//            val response= repo.getListHistory(fecha)
//            Log.e("datos"," getListHistoryresponse")
//            emit(Resource.Success(response))
//        } catch (e:Exception){
//            emit(Resource.Failure(e))
//            Log.e("error",e.message!!)
//        }
//    }

    //fun deleteAll() =repo.deleteAllProducto()
    val getUserData = repo.getUser()
    fun deleteUser()= repo.deleteUser()
    fun getStaticDataUser()= repo.getUserStatic()
    //Select data
    //fun deleteInfoService() = repo.deleteInfoService()




}
 */