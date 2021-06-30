package com.summit.core.network.model

import com.summit.core.network.model.departamento.UbiModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class AnunciosTest {
    @Test
    fun createAnuncios_shouldCorrectDatainAttributes() {

        val id = "111111"
        val img = "www.google.com"
        val fecha = 962601310L
        val descripcion = "Soy una empresa de vinos"
        val idempresa = "aqe21edsa"
        val idusuario = "asd3"
        val typeID = "asd23e23e"
        val phone = "962223945"
        val ubicacion = UbiModel()
        val type = "todos"
        val titulo = "Empresa de vinos"
        val postulaciones = listOf<String>()
        val reporte = 10
        val visualizaciones = 20
        val estado = "Pendiente"

        val add = Anuncios(
            id = id,
            img = img,
            fecha = fecha,
            descripcion = descripcion,
            idempresa = idempresa,
            idusuario = idusuario,
            typeID = typeID,
            phone = phone,
            ubicacion = ubicacion,
            type = type,
            titulo = titulo,
            postulaciones = postulaciones,
            reporte = reporte,
            visualizaciones = visualizaciones,
            estado = estado
        )

        Assertions.assertEquals(id, add.id)
        Assertions.assertEquals(img, add.img)
        Assertions.assertEquals(fecha, add.fecha)

        Assertions.assertEquals(descripcion, add.descripcion)
        Assertions.assertEquals(idusuario, add.idusuario)
        Assertions.assertEquals(idempresa, add.idempresa)
        Assertions.assertEquals(typeID, add.typeID)
        Assertions.assertEquals(phone, add.phone)
        Assertions.assertEquals(ubicacion, add.ubicacion)
        Assertions.assertEquals(type, add.type)
        Assertions.assertEquals(titulo, add.titulo)
        Assertions.assertEquals(postulaciones, add.postulaciones)
        Assertions.assertEquals(reporte, add.reporte)
        Assertions.assertEquals(visualizaciones, add.visualizaciones)
        Assertions.assertEquals(estado, add.estado)


    }
}