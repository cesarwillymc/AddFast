package com.summit.core.network.repository

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.network.model.Anuncios
import com.summit.core.network.model.departamento.UbiModel
import com.summit.core.network.model.departamento.UbicacionModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
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

    @MockK(relaxed = true)
    private lateinit var dataUpload: UploadTask.TaskSnapshot
    private val ubication = UbicacionModel("puno", "puno", 0)
    private val add = listOf(
        Anuncios(
            "Es una empresa nueva", 1010000, "ADafcd", "213123112", "21313121", "www.google.com", "962601310",
            UbiModel(0.0, 0.0), "category a", "title1", "venta de vino 2", listOf(), 0, 1, "PENDIENTE"
        ),
        Anuncios(
            "Es una empresa", 100000, "AD", "21312312", "2131321", "www.google.com", "962601310",
            UbiModel(0.0, 0.0), "category", "title", "venta de vino", listOf(), 0, 1, "PENDIENTE"
        )
    )


    @BeforeEach
    fun setUp() {

        MockKAnnotations.init(this)

        adRepository = AdRepositoryImpl(firestore, db, storage)
    }

    @AfterEach
    fun destroy() {
        clearMocks(firestore, db, storage)
        clearStaticMockk()
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

        coEvery { datoSnap.toObject(Anuncios::class.java) } returns add[1]
        // WHEN
        val response = adRepository.getAnuncioId("01sm3zv3aCzGrWX3ycPT")
        // THEN
        Truth.assertThat(response).isEqualTo(add[1])
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
        val id = "01sm3zv3aCzGrWXa3ycPT"
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
                .update("visualizaciones", dataFildValue).await()
        } returns mockk<Void>()
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

    @Test
    fun `Create add,should return complete secuence`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        //GIVEN
        val id = "ideas223ed"
        val departament = "puno"
        val provincia = "chicuito"
        coEvery {
            firestore.collection(departament)
                .document(provincia)
                .collection("anuncios").add(add[0]).await().id
        } returns id
        coEvery {
            firestore.collection(departament)
                .document(provincia)
                .collection("anuncios").document(id).update("id", id).await()
        } returns mockk<Void>()
        //WHEN
        adRepository.crearAnuncio(add[0], departament, provincia)

        //THEN
        coVerifyOrder {
            firestore.collection(departament)
                .document(provincia)
                .collection("anuncios").add(add[0]).await().id
            firestore.collection(departament)
                .document(provincia)
                .collection("anuncios").document(id).update("id", id).await()
        }
    }

    @Test
    fun `Create add,should return error first`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        //GIVEN
        val id = "ideas223ed"
        val departament = "puno"
        val provincia = "chicuito"
        coEvery {
            firestore.collection(departament)
                .document(provincia)
                .collection("anuncios").add(add[0]).await().id
        } throws Exception("Error")
        coEvery {
            firestore.collection(departament)
                .document(provincia)
                .collection("anuncios").document(id).update("id", id).await()
        } returns mockk<Void>()
        //WHEN
        val expected = try {
            adRepository.crearAnuncio(add[0], departament, provincia)
        } catch (e: Exception) {
            e
        }

        //THEN
        Truth.assertThat(expected).isInstanceOf(Exception::class.java)
    }

    @Test
    fun `Create add,should return error second `() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        //GIVEN
        val id = "ideas223ed"
        val departament = "puno"
        val provincia = "chicuito"
        coEvery {
            firestore.collection(departament)
                .document(provincia)
                .collection("anuncios").add(add[0]).await().id
        } returns id
        coEvery {
            firestore.collection(departament)
                .document(provincia)
                .collection("anuncios").document(id).update("id", id).await()
        } throws Exception("Error")
        //WHEN
        val expected = try {
            adRepository.crearAnuncio(add[0], departament, provincia)
        } catch (e: Exception) {
            e
        }

        //THEN
        Truth.assertThat(expected).isInstanceOf(Exception::class.java)
    }

    @Test
    fun `Upload photo and get URL, should return String url`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        mockkStatic("android.net.Uri")
        //GIVEN
        val file = mockk<File>()
        val uri = mockk<Uri>()
        val path = "mockk<String>()"
        val response = "response data"
        every {
            "images/${file.name}"
        } returns path
        every {
            Uri.fromFile(file)
        } returns uri
        coEvery {
            storage.getReference(path).putFile(uri).await()
        } returns dataUpload

        coEvery {
            storage.getReference(path).downloadUrl.await().toString()
        } returns response

        //WHEN

        adRepository.uploadFotoAnuncio(file)


        //THEN
        coVerifyOrder {
            storage.getReference(path).putFile(uri).await()
            storage.getReference(path).downloadUrl.await().toString()
        }

    }

    @Test
    fun `Upload photo and get URL, should return error`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        mockkStatic("android.net.Uri")
        //GIVEN
        val file = mockk<File>()
        val uri = mockk<Uri>()
        val path = "mockk<String>()"
        val response = "response data"
        every {
            "images/${file.name}"
        } throws Exception("error")
        every {
            Uri.fromFile(file)
        } returns uri
        coEvery {
            storage.getReference(path).putFile(uri).await()
        } throws Exception("error")

        coEvery {
            storage.getReference(path).downloadUrl.await().toString()
        } returns response

        //WHEN

        val expect = try {
            adRepository.uploadFotoAnuncio(file)
        } catch (e: Exception) {
            e
        }

        //THEN
        Truth.assertThat(expect).isInstanceOf(Exception::class.java)
    }

    @Test
    fun `Get Adds , should return list adds`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        val dataMock = mockk<QuerySnapshot>()
        //GIVEN
        val id = "sad2323"
        every { db.selectUbicacionModelStatic() } returns ubication

        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios")
                .whereEqualTo("idempresa", id)
                .get().await()
        } returns dataMock

        coEvery { dataMock.toObjects(Anuncios::class.java) } returns add
        // WHEN
        val response = adRepository.getMisAnuncios(id)
        // THEN
        Truth.assertThat(response).isEqualTo(add)
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").whereEqualTo("idempresa", id)
                .get().await()
        }
        verify(exactly = 1) { dataMock.toObjects(Anuncios::class.java)!! }

    }

    @Test
    fun `Get Adds , should return error`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        val dataMock = mockk<QuerySnapshot>()
        //GIVEN
        val id = "sad2323"
        every { db.selectUbicacionModelStatic() } returns ubication

        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios")
                .whereEqualTo("idempresa", id)
                .get().await()
        } throws Exception("List error")

        coEvery { dataMock.toObjects(Anuncios::class.java) } returns add
        // WHEN
        val response = try {
            adRepository.getMisAnuncios(id)
        } catch (e: Exception) {
            e
        }
        // THEN
        Truth.assertThat(response).isInstanceOf(Exception::class.java)
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").whereEqualTo("idempresa", id)
                .get().await()
        }
        verify(exactly = 0) { dataMock.toObjects(Anuncios::class.java)!! }

    }
}