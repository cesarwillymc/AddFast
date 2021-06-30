package com.summit.core.network.repository

import androidx.lifecycle.LiveData
import com.summit.core.network.model.departamento.ProvinciaItem
import com.summit.core.network.model.departamento.UbicacionModel

interface GpsRepository {
    fun deleteUbicacion()
    fun saveUbicacion(perfilUsuario: UbicacionModel)
    fun updateUbicacionAppDb(perfilUsuario: UbicacionModel)
    fun getUbicacion(): LiveData<UbicacionModel>
    fun getUbicacionStatic(): UbicacionModel
    suspend fun verDepartamento(): List<ProvinciaItem>?

    suspend fun verProvincia(id: String): List<ProvinciaItem>?
}