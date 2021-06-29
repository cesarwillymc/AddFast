package com.summit.core.network.model.departamento


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NAME_TABLE_MAPEO")
data class UbicacionModel(
    var departamento:String = "Puno",
    var provincia: String = "Puno",
    @PrimaryKey(autoGenerate = false)
    var _id:Int=0
)