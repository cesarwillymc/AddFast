/*
package com.summit.android.addfast.repo.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.summit.android.addfast.utils.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constants.NAME_TABLE_USER)
data class Usuario(
    var name:String? = null,
    var dni: String? = null,
    var phone:String?=null,
    var nameEmpresa: String? = null,
    var ruc: String? = null,
    var uriImgPerfil: String? = null,
    var reportes: Int = 0,
    var accountactivate: Boolean = true,
    @PrimaryKey(autoGenerate = false)
    var _id:String="",
    var admin:Boolean?=null
):Parcelable
 */