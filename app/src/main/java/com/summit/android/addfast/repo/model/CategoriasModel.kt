package com.summit.android.addfast.repo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoriasModel(
    var id:String="",
    var name:String="",
    var img:String="",
    var cantidad:Int=0

    ):Parcelable