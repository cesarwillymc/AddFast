package com.summit.core.network.model

data class Reporte(
        val idanuncio:String,
        val idempresa:String,
        val idreportante:String,
        val message:String,
        val titleanuncio:String,
        val fecha:Long
)