package com.summit.core.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PostulacionTest {
    @Test
    fun createPostulate_shouldCorrectDatainAttributes() {
        val idanuncio = "2312"
        val id = "111111"
        val state = true
        val img = "www.google.com"
        val name = "Venta de vinos"
        val fecha = 962601310L
        val diferencia ="Soy empresario"
        val estado ="pendiente"
        val idpostulante ="adasd"
        val idempresa ="sado23"
        val respecialidad ="desarrollador"
        val titulo ="Desarrrollador de software"
        val descripcion ="Soy especialista en android"
        val phonepostulante ="9626013010"
        val rcv ="Resumen del cv"
        val archivopdf ="www.google.com"
        val imganuncio ="www.google.com"
        val imgpostulante ="www.google.com"

        val postulate = Postulacion(
            idanuncio = idanuncio,
            id = id,
            name = name,
            fecha = fecha,
            diferencia =diferencia,
            estado =estado,
            idpostulante =idpostulante,
            idempresa =idempresa,
            respecialidad =respecialidad,
            titulo =titulo,
            descripcion =descripcion,
            phonepostulante =phonepostulante,
            rcv =rcv,
            archivopdf =archivopdf,
            imganuncio =imganuncio,
            imgpostulante =imgpostulante
        )

        Assertions.assertEquals(idanuncio, postulate.idanuncio)
        Assertions.assertEquals(id, postulate.id)
        Assertions.assertEquals(name, postulate.name)
        Assertions.assertEquals(fecha, postulate.fecha)
        Assertions.assertEquals(imgpostulante, postulate.imgpostulante)
        Assertions.assertEquals(imganuncio, postulate.imganuncio)
        Assertions.assertEquals(archivopdf, postulate.archivopdf)
        Assertions.assertEquals(rcv, postulate.rcv)
        Assertions.assertEquals(phonepostulante, postulate.phonepostulante)
        Assertions.assertEquals(descripcion, postulate.descripcion)
        Assertions.assertEquals(titulo, postulate.titulo)
        Assertions.assertEquals(respecialidad, postulate.respecialidad)
        Assertions.assertEquals(idempresa, postulate.idempresa)
        Assertions.assertEquals(idpostulante, postulate.idpostulante)
        Assertions.assertEquals(estado, postulate.estado)
        Assertions.assertEquals(diferencia, postulate.diferencia)




    }
}