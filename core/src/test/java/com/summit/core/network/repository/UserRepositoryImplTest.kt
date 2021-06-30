package com.summit.core.network.repository

import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import com.summit.core.db.dao.UsuarioDao
import com.summit.core.network.model.Usuario
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryImplTest {

    @MockK(relaxed = true)
    private lateinit var db: UsuarioDao

    @MockK(relaxed = true)
    private lateinit var api: AGConnectAuth

    @MockK(relaxed = true)
    private lateinit var storage: FirebaseStorage

    private lateinit var userRepo: UserRepositoryImpl

    private val usuario = Usuario(
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
    fun setUp() {
        MockKAnnotations.init(this)
        userRepo = UserRepositoryImpl(db, storage, api)
    }

    @After
    fun destroy() {
        clearMocks(api, db, storage)

    }

    @Test
    fun testInsertUser()= runBlockingTest {

        userRepo.insertUser(usuario)

        coVerify  { db.insertUsuario(usuario) }

    }

}