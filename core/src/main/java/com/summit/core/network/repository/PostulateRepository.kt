package com.summit.core.network.repository

import com.summit.core.network.model.Postulacion
import com.summit.core.network.model.Usuario
import java.io.File

interface PostulateRepository {
    suspend fun getAllPostulante(): List<Usuario>
    suspend fun getAllPostulanteByPalabra(palabra: String): List<Usuario>

    suspend fun postularAnuncio(id: String, idPostulacion: String)

    suspend fun uploadCurriculumPostulacion(cv: File) : String

    suspend fun crearPostulacion(postulacion: Postulacion): String
    suspend fun cambiarEstadoPostulacion(id: String,message:String)

    suspend fun verMisPostulaciones(id: String): List<Postulacion>

    suspend fun verPostulacionesdemiAnuncio(idAnuncio: String): List<Postulacion>
}