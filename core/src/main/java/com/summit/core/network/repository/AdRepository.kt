package com.summit.core.network.repository


import com.summit.core.network.model.Anuncios
import com.summit.core.status.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AdRepository {
    suspend fun cambiarEstadoAnuncio(id: String,message:String): Unit

    suspend fun getAllAnunciosByPalabra(palabra: String): List<Anuncios>
    suspend fun getAllAnuncios(): List<Anuncios>
    suspend fun getAllAnunciosPost(): List<Anuncios>
    suspend fun getAnuncioId(id: String): Anuncios
    suspend fun aumentarVisualizacionesAnuncios(id: String)

    suspend fun reportarAnuncio(id: String)

    suspend fun crearAnuncio(anuncios: Anuncios, departamento: String, provincia: String)
    suspend fun crearAnunciodata( departamento: String, provincia: String)

    suspend fun uploadFotoAnuncio(imagen: File) : Flow<Resource<String>>

    suspend fun getMisAnuncios(id: String): List<Anuncios>

}