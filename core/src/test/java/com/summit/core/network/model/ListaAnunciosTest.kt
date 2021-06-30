package com.summit.core.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ListaAnunciosTest{
    @Test
    fun createListAnuncios_shouldCorrectDatainAttributes(){

        val id="111111"
        val name="Venta de vinos"
        val lista= listOf<Anuncios>()

        val listAdds= ListaAnuncios(
            id = id,
            name = name,
            lista=lista
        )

        Assertions.assertEquals(lista ,listAdds.lista)
        Assertions.assertEquals(id, listAdds.id)
        Assertions.assertEquals(name, listAdds.name)

    }
}