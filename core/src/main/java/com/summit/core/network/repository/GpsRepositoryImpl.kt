package com.summit.core.network.repository

import com.beust.klaxon.Klaxon
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.AppDB
import com.summit.core.json.Constants
import com.summit.core.network.model.departamento.ProvinciaItem
import com.summit.core.network.model.departamento.UbicacionModel

internal class GpsRepositoryImpl(
    private val db: AppDB
):GpsRepository {
    override fun deleteUbicacion() = db.ubicacionModelDao.deleteUbicacionModel()
    override fun saveUbicacion(perfilUsuario: UbicacionModel) = db.ubicacionModelDao.insertUbicacionModel(perfilUsuario)
    override fun updateUbicacionAppDb(perfilUsuario: UbicacionModel) = db.ubicacionModelDao.updateUbicacionModel(perfilUsuario)
    override fun getUbicacion() = db.ubicacionModelDao.selectUbicacionModel()
    override fun getUbicacionStatic() = db.ubicacionModelDao.selectUbicacionModel()
    override suspend fun verDepartamento(): List<ProvinciaItem>? {
        return Klaxon().parseArray<ProvinciaItem>(Constants.departamentoJson)
    }

    override suspend fun verProvincia(id: String): List<ProvinciaItem>? {
        val datos = mutableListOf<ProvinciaItem>()
        Klaxon().parseArray<ProvinciaItem>(Constants.provinciaJson)?.forEach {
            if (it.department_id == id) {
                datos.add(it)
            }
        }
        return datos
    }
}