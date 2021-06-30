package com.summit.core.network.repository

import com.google.common.truth.Truth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.network.model.Postulacion
import com.summit.core.network.model.departamento.UbicacionModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
internal class PostulateRepositoryImplTest {

    @MockK(relaxed = true)
    private lateinit var db: UbicacionModelDao

    @MockK(relaxed = true)
    private lateinit var firestore: FirebaseFirestore

    @MockK(relaxed = true)
    private lateinit var storage: FirebaseStorage

    private lateinit var postulateRepositoryImpl: PostulateRepositoryImpl

    private val ubication = UbicacionModel("puno", "puno", 0)

    @MockK(relaxed = true)
    private lateinit var dataFildValue: FieldValue

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        postulateRepositoryImpl = PostulateRepositoryImpl(db, firestore, storage)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(db, firestore, storage)
        clearStaticMockk()
    }

    private val listPostulate =
        listOf(Postulacion("diference", "estado", 10000L, "sadsad", "asdsa", "sadsad", "id", "gaviotas", "es una peli"))

    @Test
    fun `postularAnuncio, should return complete`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        mockkStatic("com.google.firebase.firestore.FieldValue")
        val datoSnap = mockk<QuerySnapshot>()
        val id = "ASdsdad"
        val idPostulate = "dsadsad213123"

        //GIVEN
        coEvery {
            FieldValue.arrayUnion(idPostulate)
        } returns dataFildValue
        every { db.selectUbicacionModelStatic() } returns ubication
        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document(id)
                .update("postulaciones", dataFildValue).await()
        } returns mockk()
        //WHEN
        postulateRepositoryImpl.postularAnuncio(id, idPostulate)
        //THEN
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document(id)
                .update("postulaciones", dataFildValue).await()
        }

    }

    @Test
    fun `postularAnuncio, should return error`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        mockkStatic("com.google.firebase.firestore.FieldValue")

        val id = "ASdsdad"
        val idPostulate = "dsadsad213123"

        //GIVEN
        coEvery {
            FieldValue.arrayUnion(idPostulate)
        } returns dataFildValue
        every { db.selectUbicacionModelStatic() } returns ubication
        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document(id)
                .update("postulaciones", dataFildValue).await()
        } throws Exception("Error")
        //WHEN
        val expected = try {
            postulateRepositoryImpl.postularAnuncio(id, idPostulate)
        } catch (e: Exception) {
            e
        }
        //THEN
        Truth.assertThat(expected).isInstanceOf(Exception::class.java)
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").document(id)
                .update("postulaciones", dataFildValue).await()
        }

    }

    @Test
    fun `crearPostulacion, should return complete`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        //GIVEN
        val model =
            Postulacion("diference", "estado", 10000L, "sadsad", "asdsa", "sadsad", "id", "gaviotas", "es una peli")

        val id = "identidicaro"
        coEvery { firestore.collection("postulaciones").add(model).await().id } returns id
        coEvery { firestore.collection("postulaciones").document(id).update("id", id).await() } returns mockk()
        //WHEN
        val response = postulateRepositoryImpl.crearPostulacion(model)

        //THEN
        assertEquals(response, id)
        coVerifyOrder {
            firestore.collection("postulaciones").add(model).await().id
            firestore.collection("postulaciones").document(id).update("id", id).await()
        }

    }

    @Test
    fun `crearPostulacion, should return error`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        //GIVEN
        val model =
            Postulacion("diference", "estado", 10000L, "sadsad", "asdsa", "sadsad", "id", "gaviotas", "es una peli")

        val id = "identidicaro"
        coEvery { firestore.collection("postulaciones").add(model).await().id } returns id
        coEvery {
            firestore.collection("postulaciones").document(id).update("id", id).await()
        } throws Exception("error")
        //WHEN
        val response = try {
            postulateRepositoryImpl.crearPostulacion(model)
        } catch (e: Exception) {
            e
        }

        //THEN
        Truth.assertThat(response).isInstanceOf(Exception::class.java)
        coVerifyOrder {
            firestore.collection("postulaciones").add(model).await().id
            firestore.collection("postulaciones").document(id).update("id", id).await()
        }

    }

    @Test
    fun `verMisPostulaciones, should return data complete`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        //GIVEN
        val id = "identificador"
        val querySnap = mockk<QuerySnapshot>()
        coEvery {
            firestore.collection("postulaciones").whereEqualTo("idpostulante", id).get().await()
        } returns querySnap
        every { querySnap.toObjects(Postulacion::class.java) } returns listPostulate
        //WHEN
        val response = postulateRepositoryImpl.verMisPostulaciones(id)
        //THEN
        assertEquals(response, listPostulate)
        coVerifyOrder {
            firestore.collection("postulaciones").whereEqualTo("idpostulante", id).get().await()
            querySnap.toObjects(Postulacion::class.java)
        }
    }

    @Test
    fun `verMisPostulaciones, should return exception`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        //GIVEN
        val id = "identificador"
        val querySnap = mockk<QuerySnapshot>()
        coEvery {
            firestore.collection("postulaciones").whereEqualTo("idpostulante", id).get().await()
        } throws Exception("Error")
        every { querySnap.toObjects(Postulacion::class.java) } returns listPostulate
        //WHEN
        val response = try {
            postulateRepositoryImpl.verMisPostulaciones(id)
        } catch (e: Exception) {
            e
        }
        //THEN
        Truth.assertThat(response).isInstanceOf(Exception::class.java)
        coVerify(exactly = 1) { firestore.collection("postulaciones").whereEqualTo("idpostulante", id).get().await() }
        coVerify(exactly = 0) { querySnap.toObjects(Postulacion::class.java) }

    }
}