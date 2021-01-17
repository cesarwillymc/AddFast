package com.summit.android.addfast.repo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Promociones(
    var id:String="",
    var state:Boolean=true,
    var idanuncio:String="",
    var img:String="",
    var name:String="",
    var fecha:Long=0L
):Parcelable