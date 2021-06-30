package com.summit.core.network.repository


import com.summit.core.network.model.Anuncios
import java.io.File

interface AdRepository {



    suspend fun getAnuncioId(id: String): Anuncios

    suspend fun aumentarVisualizacionesAnuncios(id: String)

    suspend fun crearAnuncio(anuncios: Anuncios, departamento: String, provincia: String)

    suspend fun uploadFotoAnuncio(imagen: File) : String

    suspend fun getMisAnuncios(id: String): List<Anuncios>

}