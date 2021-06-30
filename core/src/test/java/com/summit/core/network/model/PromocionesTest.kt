package com.summit.core.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PromocionesTest{
    @Test
    fun createPromocion_shouldCorrectDatainAttributes(){
        val idanuncio="2312"
        val id="111111"
        val state=true
        val img="www.google.com"
        val name="Venta de vinos"
        val fecha=962601310L

        val promo= Promociones(
            idanuncio = idanuncio,
            id = id,
            state = state,
            img = img,
            name = name,
            fecha = fecha
        )

        Assertions.assertEquals(idanuncio ,promo.idanuncio)
        Assertions.assertEquals(id, promo.id)
        Assertions.assertEquals(state, promo.state)
        Assertions.assertEquals(img, promo.img)
        Assertions.assertEquals(name, promo.name)
        Assertions.assertEquals(fecha, promo.fecha)

    }
}