package com.summit.core.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.summit.core.db.AppDB
import com.summit.core.network.model.Usuario
import com.summit.test_utils.livedata.getValue
import com.summit.test_utils.robo.TestRobolectric
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotSame
import kotlinx.coroutines.runBlocking
import org.junit.*


class UsuarioDaoTest : TestRobolectric() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDB
    private lateinit var usuarioDao: UsuarioDao
    private val fakeUser = Usuario(
        "Cesar Mamani Canaza",
        "00000010",
        "962601310",
        "Summit Dev",
        ruc = "10703217015",
        admin = false,
        uriImgPerfil = "https://summit-puno.s3.us-east-2.amazonaws.com/yachay/boy.webp",
        _id = "invitado",
        reportes = 0,
        accountactivate = true
    )
    private val fakeUser2 = Usuario(
        "Gilmar Gustabo Mendoza",
        "00001010",
        "962601311",
        "Sistemas Dev",
        ruc = "10703267015",
        admin = false,
        uriImgPerfil = "https://summit-puno.s3.us-east-2.amazonaws.com/yachay/boy.webp",
        _id = "invitado2",
        reportes = 10,
        accountactivate = false
    )


    @Before
    fun setUp() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room
            .inMemoryDatabaseBuilder(context, AppDB::class.java)
            .allowMainThreadQueries()
            .build()
        usuarioDao = database.usuarioDao()
    }

    @After
    fun destroy() {
        database.close()
    }

    @Test
    fun `obtain user, should return null`() {
        val user = usuarioDao.selectUsuarioStatic()
        Assert.assertTrue(user == null)
    }

    @Test
    fun `insert user and obtain user, should return data and equals data`() {
        usuarioDao.insertUsuario(fakeUser)
        val usuario = usuarioDao.selectUsuarioStatic()
        assertEquals(fakeUser, usuario)
    }

    @Test
    fun `insert user and obtain user, should return data not equals`() {
        usuarioDao.insertUsuario(fakeUser)
        val user = usuarioDao.selectUsuarioStatic()
        assertNotSame(fakeUser2, user)
    }

    @Test
    fun `insert user and delete data, should return null`() {
        usuarioDao.insertUsuario(fakeUser)
        val user = usuarioDao.selectUsuarioStatic()
        Assert.assertTrue(user != null)
        usuarioDao.deleteUsuario()
        val userTime = usuarioDao.selectUsuarioStatic()
        Assert.assertTrue(userTime == null)
    }

    @Test
    fun `insert user and update user, should return not equals value`() {
        usuarioDao.insertUsuario(fakeUser)
        val user = usuarioDao.selectUsuarioStatic()
        assertEquals(fakeUser, user)
        val data = fakeUser
        data.accountactivate = false
        data.reportes = 30
        usuarioDao.updateUsuario(data)
        val userUpdated = usuarioDao.selectUsuarioStatic()
        assertEquals(data, userUpdated!!)
    }

    @Test
    fun `select data with livedata, should return value`() {
        usuarioDao.insertUsuario(fakeUser)
        val user = usuarioDao.selectUsuario()
        assertEquals(fakeUser, getValue(user))

    }

}