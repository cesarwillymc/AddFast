package com.summit.android.addfast.repo.model.departamento


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.summit.android.addfast.utils.Constants

@Entity(tableName = Constants.NAME_TABLE_MAPEO)
data class UbicacionModel(
    var departamento:String = "Puno",
    var provincia: String = "Puno",
    @PrimaryKey(autoGenerate = false)
    var _id:Int=0
)