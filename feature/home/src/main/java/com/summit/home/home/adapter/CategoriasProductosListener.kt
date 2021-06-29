
package com.summit.home.home.adapter


import com.summit.core.network.model.Anuncios
import com.summit.core.network.model.ListaAnuncios


interface CategoriasProductosListener {
    fun onClickVerMas(dato: ListaAnuncios, position: Int)
    fun onCLickItem(dato: Anuncios, position: Int)
}
