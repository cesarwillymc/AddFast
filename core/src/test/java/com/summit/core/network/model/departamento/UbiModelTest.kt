package com.summit.core.network.model.departamento

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class UbiModelTest{
    @Test
    fun createUbiModel_shouldCorrectDatainAttributes(){

        val longitude=111111.0
        val latitude=1111111111.0

        val ubiModel= UbiModel(
            latitude = latitude,
            longitude = longitude
        )

        Assertions.assertEquals(latitude ,ubiModel.latitude)
        Assertions.assertEquals(longitude, ubiModel.longitude)

    }
}