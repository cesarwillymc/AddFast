package com.summit.core.network.model.departamento

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class UbicacionModelTest {
    @Test
    fun createUbication_shouldCorrectDatainAttributes() {
        val departamento = "Puno"
        val provincia = "Huancane"
        val _id = 21312

        val ubication = UbicacionModel(
            departamento = departamento,
            provincia = provincia,
            _id = _id

        )

        Assertions.assertEquals(provincia, ubication.provincia)
        Assertions.assertEquals(departamento, ubication.departamento)
        Assertions.assertEquals(_id, ubication._id)

    }
}