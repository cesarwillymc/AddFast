package com.summit.core.network.api


import com.summit.core.network.model.Anuncios
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {

    @GET("{departament}/{province}/anuncios/{addid}.json")
    suspend fun getAddById(
        @Path("departament") departament: String,
        @Path("province") province: String,
        @Path("addid") addid: String
    ): Anuncios
}