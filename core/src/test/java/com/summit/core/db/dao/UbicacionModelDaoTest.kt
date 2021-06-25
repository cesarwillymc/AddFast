package com.summit.core.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.summit.core.db.AppDB
import com.summit.core.network.model.departamento.UbicacionModel
import com.summit.test_utils.livedata.getValue
import com.summit.test_utils.robo.TestRobolectric
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotSame
import kotlinx.coroutines.runBlocking
import org.junit.*


class UbicacionModelDaoTest: TestRobolectric()  {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDB
    private lateinit var ubicacionModelDao: UbicacionModelDao
    private val fakeUbication = listOf(
        UbicacionModel("Lima","San Miguel",0),
        UbicacionModel("Arequipa","Joya",1),
        UbicacionModel("Puno","Puno",1),
    )

    @Before
    fun setUp() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room
            .inMemoryDatabaseBuilder(context, AppDB::class.java)
            .allowMainThreadQueries()
            .build()
        ubicacionModelDao = database.ubicacionModelDao()
    }

    @After
    fun destroy() {
        database.close()
    }
    @Test
    fun `obtain ubication, should return null`() {
        val characters = ubicacionModelDao.selectUbicacionModel()
        Assert.assertTrue(getValue(characters)==null)
    }
    @Test
    fun `insert ubication and obtain ubication, should return data and equals data`() {
        ubicacionModelDao.insertUbicacionModel(fakeUbication[0])
        val characters = ubicacionModelDao.selectUbicacionModel()
        assertEquals(fakeUbication[0], getValue(characters))
    }
    @Test
    fun `insert ubication and obtain ubication, should return data not equals`() {
        ubicacionModelDao.insertUbicacionModel(fakeUbication[0])
        val characters = ubicacionModelDao.selectUbicacionModel()
        assertNotSame(fakeUbication[1], getValue(characters))
    }
    @Test
    fun `insert ubication and delete ubication, should return null`() {
        ubicacionModelDao.insertUbicacionModel(fakeUbication[0])
        val characters = ubicacionModelDao.selectUbicacionModel()
        Assert.assertTrue(getValue(characters)!=null)
        ubicacionModelDao.deleteUbicacionModel()
        Assert.assertTrue(getValue(characters)==null)
    }
    @Test
    fun `insert ubication and update ubication, should return not equals value`() {
        ubicacionModelDao.insertUbicacionModel(fakeUbication[0])
        val ubication = ubicacionModelDao.selectUbicacionModel()
        assertEquals(fakeUbication[0], getValue(ubication))
        val data=fakeUbication[0]
        data.departamento="Acapulco"
        ubicacionModelDao.updateUbicacionModel(data)
        val ubicationUpdated = ubicacionModelDao.selectUbicacionModel()
        assertEquals(data, getValue(ubicationUpdated))
    }
    @Test
    fun `select data without livedata, should return value`() {
        ubicacionModelDao.insertUbicacionModel(fakeUbication[0])
        val ubication = ubicacionModelDao.selectUbicacionModelStatic()
        assertEquals(fakeUbication[0], ubication)

    }

}