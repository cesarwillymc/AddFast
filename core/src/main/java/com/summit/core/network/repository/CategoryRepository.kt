package com.summit.core.network.repository


import com.summit.core.network.model.Anuncios
import com.summit.core.network.model.CategoriasModel

interface CategoryRepository {
    suspend fun getAllCategorias(): List<CategoriasModel>
    suspend fun getAnunciosByCategorias(id: String): List<Anuncios>

    suspend fun getAllAnunciosByCategorias(id: String): List<Anuncios>


}