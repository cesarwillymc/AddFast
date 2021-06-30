package com.summit.core.network.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.clearStaticMockk
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class AuthRepositoryImplTest {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    private lateinit var firestore: FirebaseFirestore

    @MockK(relaxed = true)
    private lateinit var auth: AGConnectAuth

    @MockK(relaxed = true)
    private lateinit var storage: FirebaseStorage

    private lateinit var authRepo:AuthRepositoryImpl
    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        authRepo= AuthRepositoryImpl(auth,firestore,storage)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(firestore, auth, storage)
        clearStaticMockk()
    }


    @Test
    fun `sendNumberCode, send code correct`()= runBlockingTest {

    }

    @Test
    fun sendVerifyCode() {
    }

    @Test
    fun createUserVerifyCode() {
    }

    @Test
    fun getDataInformation() {
    }

    @Test
    fun createDataInformation() {
    }

    @Test
    fun uploadImageProfile() {
    }
}