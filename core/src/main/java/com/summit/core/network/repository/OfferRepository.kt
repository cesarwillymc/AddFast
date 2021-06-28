package com.summit.core.network.repository


import com.summit.core.network.model.Promociones
interface OfferRepository {
    suspend fun getAllPromociones(): List<Promociones>
    suspend fun getUrlDownloadFile(path: String): String

    suspend fun getPromocion(): List<Promociones>

}