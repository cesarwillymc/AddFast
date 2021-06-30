package com.summit.core.network.repository

import com.google.common.truth.Truth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.network.model.Promociones
import com.summit.core.network.model.departamento.UbicacionModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OfferRepositoryImplTest {
    @MockK(relaxed = true)
    private lateinit var db: UbicacionModelDao
    @MockK(relaxed = true)
    private lateinit var firestore: FirebaseFirestore
    @MockK(relaxed = true)
    private lateinit var storage: FirebaseStorage

    private lateinit var offerRepo:OfferRepositoryImpl

    private val ubication = UbicacionModel("puno", "puno", 0)

    private val listPromo = listOf(Promociones("ide",true,"asdasd","google.com","promo",213123L))

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        offerRepo = OfferRepositoryImpl(db, firestore, storage)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(db, firestore, storage)
        clearStaticMockk()
    }

    @Test
    fun `get all offerts, should return list data`()= runBlockingTest {
            mockkStatic("kotlinx.coroutines.tasks.TasksKt")

            val datoSnap= mockk<QuerySnapshot>()
            val id="ASdsdad"

            //GIVEN
            every { db.selectUbicacionModelStatic() } returns ubication

            coEvery {
                firestore.collection(ubication.departamento)
                    .document(ubication.provincia).collection("promocion").whereEqualTo("state", true)
                    .get().await()
            } returns datoSnap

            coEvery { datoSnap.toObjects(Promociones::class.java) } returns listPromo
            // WHEN
            val response = offerRepo.getAllPromociones()
            // THEN
            Truth.assertThat(response).isEqualTo(listPromo)
            coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
            coVerify(exactly = 1) {
                firestore.collection(ubication.departamento)
                    .document(ubication.provincia).collection("promocion").whereEqualTo("state", true)
                    .get().await()
            }
            verify(exactly = 1) { datoSnap.toObjects(Promociones::class.java)!! }

        }
    @Test
    fun `get all offerts, should return exception`()= runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")

        val datoSnap= mockk<QuerySnapshot>()
        val id="ASdsdad"

        //GIVEN
        every { db.selectUbicacionModelStatic() } returns ubication

        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("promocion").whereEqualTo("state", true)
                .get().await()
        } throws Exception("Error")

        coEvery { datoSnap.toObjects(Promociones::class.java) } returns listPromo
        // WHEN
        val response = try{offerRepo.getAllPromociones()}catch (e:Exception){e}
        // THEN
        Truth.assertThat(response).isInstanceOf(Exception::class.java)
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("promocion").whereEqualTo("state", true)
                .get().await()
        }
        verify(exactly = 0) { datoSnap.toObjects(Promociones::class.java)!! }

    }

}