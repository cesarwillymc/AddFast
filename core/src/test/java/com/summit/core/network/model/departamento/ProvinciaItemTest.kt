package com.summit.core.network.model.departamento

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ProvinciaItemTest{
    @Test
    fun createProvince_shouldCorrectDatainAttributes(){
        val department_id="2312"
        val id="111111"
        val name="Venta de vinos"

        val province= ProvinciaItem(
            department_id = department_id,
            id = id,
            name = name,
        )

        Assertions.assertEquals(department_id ,province.department_id)
        Assertions.assertEquals(id, province.id)
        Assertions.assertEquals(name, province.name)

    }
}