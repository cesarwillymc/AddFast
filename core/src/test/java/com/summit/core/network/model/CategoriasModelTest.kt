package com.summit.core.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CategoriasModelTest{
    @Test
    fun createCategoryModel_shouldCorrectDatainAttributes(){

        val id="111111"
        val img="www.google.com"
        val name="Venta de vinos"
        val cantidad=962601310

        val category= CategoriasModel(
            id = id,
            img = img,
            name = name,
            cantidad = cantidad
        )

        Assertions.assertEquals(cantidad ,category.cantidad)
        Assertions.assertEquals(id, category.id)
        Assertions.assertEquals(img, category.img)
        Assertions.assertEquals(name, category.name)

    }
}