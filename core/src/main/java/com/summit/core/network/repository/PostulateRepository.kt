package com.summit.core.network.repository

import com.summit.core.network.model.Postulacion
import java.io.File

interface PostulateRepository {



    suspend fun postularAnuncio(id: String, idPostulacion: String)

    suspend fun uploadCurriculumPostulacion(cv: File) : String

    suspend fun crearPostulacion(postulacion: Postulacion): String

    suspend fun verMisPostulaciones(id: String): List<Postulacion>

}