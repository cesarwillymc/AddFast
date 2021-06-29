package com.summit.core.db

import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.db.dao.UsuarioDao
import com.summit.test_utils.robo.TestRobolectric
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class AppDBTest : TestRobolectric(){
    @MockK
    lateinit var appdb: AppDB
    @MockK
    lateinit var usuarioDao: UsuarioDao
    @MockK
    lateinit var locationDao: UbicacionModelDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun obtainUbicarionDao() {
        every { appdb.ubicacionModelDao() } returns locationDao

        MatcherAssert.assertThat(
            appdb.ubicacionModelDao()   ,
            CoreMatchers.instanceOf(UbicacionModelDao::class.java)
        )
    }

    @Test
    fun obtainUserDao() {
        every { appdb.usuarioDao() } returns usuarioDao

        MatcherAssert.assertThat(
            appdb.usuarioDao()   ,
            CoreMatchers.instanceOf(UsuarioDao::class.java)
        )
    }
}