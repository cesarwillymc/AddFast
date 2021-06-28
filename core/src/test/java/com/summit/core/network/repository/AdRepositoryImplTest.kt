package com.summit.core.network.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.network.model.Anuncios
import com.summit.core.network.model.departamento.UbicacionModel
import com.summit.test_utils.utils.await
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.Rule


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdRepositoryImplTest {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    private var firestore: FirebaseFirestore = mockk()

    @MockK(relaxed = true)
    private var db: UbicacionModelDao = mockk()

    @MockK(relaxed = true)
    private var storage: FirebaseStorage = mockk()


    private lateinit var adRepository: AdRepositoryImpl

    private val datoSnap: DocumentSnapshot = mockk()
    private val dataTask: Task<DocumentSnapshot> = mockk()
    private val ubication = UbicacionModel("puno", "puno", 0)

    @BeforeEach
    fun setUp() {
        adRepository = AdRepositoryImpl(firestore, db, storage)
    }

    @AfterEach
    fun destroy() {
        clearMocks(firestore, db, storage)

    }


    @Test
    fun `Get Add by id incorrect, should return Exception`() = runBlockingTest {
        //GIVEN
        every {
            db.selectUbicacionModelStatic()
        } returns UbicacionModel("puno", "puno", 0)
        val ubication = db.selectUbicacionModelStatic()
        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document("id").get()
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
                .document(ubication.provincia).collection("anuncios").document("id").get()
        }
    }

    @Test
    fun `Get Add by id correct, should return data`() = runBlockingTest {
        //GIVEN
        every { db.selectUbicacionModelStatic() } returns ubication

        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document("01sm3zv3aCzGrWX3ycPT").get()
        } returns dataTask

        every { datoSnap.toObject(Anuncios::class.java)!! } returns Anuncios()
        // WHEN
        adRepository.getAnuncioId("01sm3zv3aCzGrWX3ycPT").apply {
            // THEN
            Truth.assertThat(this).isEqualTo(Anuncios())
            coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
            coVerify(exactly = 1) {
                firestore.collection(ubication.departamento)
                    .document(ubication.provincia).collection("anuncios").document("01sm3zv3aCzGrWX3ycPT").get().await()
            }
            verify(exactly = 1) { datoSnap.toObject(Anuncios::class.java)!! }
        }


    }


}