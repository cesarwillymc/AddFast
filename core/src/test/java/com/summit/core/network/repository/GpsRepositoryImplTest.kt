package com.summit.core.network.repository

import com.beust.klaxon.Klaxon
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.json.Constants
import com.summit.core.network.model.departamento.ProvinciaItem
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
internal class GpsRepositoryImplTest {
    @MockK(relaxed = true)
    private lateinit var db: UbicacionModelDao

    private lateinit var gpsRepo: GpsRepositoryImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        gpsRepo = GpsRepositoryImpl(db)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(db)
        clearStaticMockk()
    }

    private val listProvince =
        listOf(ProvinciaItem("huancane", "as213213", "huancane"), ProvinciaItem("azangaro", "as213aa213", "azangaro"))


    @Test
    fun `get departament, error diferent data`()= runBlockingTest {
        val klaxon=mockk<Klaxon>()
        //GIVEN
        every { klaxon.parseArray<ProvinciaItem>(Constants.departamentoJson) } returns listProvince
        //WHEN
        val data= gpsRepo.verDepartamento()
        //THEN
        assertNotEquals(data,listProvince)
    }

    @Test
    fun `province no found data`()= runBlockingTest {
        val klaxon=mockk<Klaxon>()
        //GIVEN
        every { klaxon.parseArray<ProvinciaItem>(Constants.provinciaJson) } returns listOf()
        //WHEN
        val data= gpsRepo.verProvincia("id")
        //THEN
        assertEquals(data,listOf<ProvinciaItem>())
    }
}