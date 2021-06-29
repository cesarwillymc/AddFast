package com.summit.core.network.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.network.model.Anuncios
import com.summit.core.network.model.departamento.UbiModel
import com.summit.core.network.model.departamento.UbicacionModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class AdRepositoryImplTest {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    private lateinit var firestore: FirebaseFirestore

    @MockK(relaxed = true)
    private lateinit var db: UbicacionModelDao

    @MockK(relaxed = true)
    private lateinit var storage: FirebaseStorage


    private lateinit var adRepository: AdRepositoryImpl

    @MockK(relaxed = true)
    private lateinit var datoSnap: DocumentSnapshot


    @MockK(relaxed = true)
    private lateinit var dataFildValue: FieldValue
    private val ubication = UbicacionModel("puno", "puno", 0)
    private val add =
        Anuncios(
            "Es una empresa", 100000, "AD", "21312312", "2131321", "www.google.com", "962601310",
            UbiModel(0.0, 0.0), "category", "title", "venta de vino", listOf(), 0, 1, "PENDIENTE"
        )

    @BeforeEach
    fun setUp() {

        MockKAnnotations.init(this)

        adRepository = AdRepositoryImpl(firestore, db, storage)
    }

    @AfterEach
    fun destroy() {
        clearMocks(firestore, db, storage)

    }


    @Test
    fun `Get Add by id incorrect, should return Exception`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        //GIVEN
        every {
            db.selectUbicacionModelStatic()
        } returns ubication
        val ubication = db.selectUbicacionModelStatic()
        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document("id").get().await()
        } throws Exception("No work")

        // WHEN
        val expected = try {
            adRepository.getAnuncioId("id")
        } catch (e: Exception) {
            e
        }
        // THEN
        Truth.assertThat(expected).isInstanceOf(Exception::class.java)
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document("id").get().await()
        }

    }

    @Test
    fun `Get Add by id correct, should return data`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        //GIVEN
        every { db.selectUbicacionModelStatic() } returns ubication

        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document("01sm3zv3aCzGrWX3ycPT").get().await()
        } returns datoSnap

        coEvery { datoSnap.toObject(Anuncios::class.java) } returns add
        // WHEN
        val response = adRepository.getAnuncioId("01sm3zv3aCzGrWX3ycPT")
        // THEN
        Truth.assertThat(response).isEqualTo(add)
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document("01sm3zv3aCzGrWX3ycPT").get().await()
        }
        verify(exactly = 1) { datoSnap.toObject(Anuncios::class.java)!! }

    }

    @Test
    fun `Increments views in add, should error`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        mockkStatic("com.google.firebase.firestore.FieldValue")
        //GIVEN
        val id="01sm3zv3aCzGrWXa3ycPT"
        every {
            db.selectUbicacionModelStatic()
        } returns ubication
        coEvery {
            FieldValue.increment(1)
        } returns dataFildValue
        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document(id)
                .update("visualizaciones", dataFildValue).await()
        } throws Exception("Error data")
        // WHEN
        val expected = try {
            adRepository.aumentarVisualizacionesAnuncios(id)
        } catch (e: Exception) {
            e
        }
        // THEN
        Truth.assertThat(expected).isInstanceOf(Exception::class.java)
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document(id)
                .update("visualizaciones", dataFildValue).await()
        }

    }

    @Test
    fun `Increments views in add, should complete`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        mockkStatic("com.google.firebase.firestore.FieldValue")
        //GIVEN
        every {
            db.selectUbicacionModelStatic()
        } returns ubication
        coEvery {
            FieldValue.increment(1)
        } returns dataFildValue
        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document("01sm3zv3aCzGrWX3ycPT")
                .update("visualizaciones",dataFildValue).await()
        } returns  mockk<Void>()
        // WHEN
        adRepository.aumentarVisualizacionesAnuncios("01sm3zv3aCzGrWX3ycPT")
        // THEN
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document("01sm3zv3aCzGrWX3ycPT")
                .update("visualizaciones", dataFildValue).await()
        }
    }


}