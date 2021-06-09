package com.summit.core.network.repository

import com.beust.klaxon.Klaxon
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.AppDB
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.json.Constants
import com.summit.core.network.model.departamento.ProvinciaItem
import com.summit.core.network.model.departamento.UbicacionModel

internal class GpsRepositoryImpl(
    private val db: UbicacionModelDao
):GpsRepository {
    override fun deleteUbicacion() = db.deleteUbicacionModel()
    override fun saveUbicacion(perfilUsuario: UbicacionModel) = db.insertUbicacionModel(perfilUsuario)
    override fun updateUbicacionAppDb(perfilUsuario: UbicacionModel) = db.updateUbicacionModel(perfilUsuario)
    override fun getUbicacion() = db.selectUbicacionModel()
    override fun getUbicacionStatic() = db.selectUbicacionModelStatic()
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