package com.summit.core.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoriasModel(
    var id: String = "",
    var name: String = "",
    var img2: String = "https://store.webkul.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/a/k/akeneo-category-image-description-webkul.png",
    var img: String = "",
    var cantidad: Int = 0

) : Parcelable