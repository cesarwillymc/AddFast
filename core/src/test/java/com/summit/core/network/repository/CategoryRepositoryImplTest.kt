package com.summit.core.network.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.network.model.Anuncios
import com.summit.core.network.model.CategoriasModel
import com.summit.core.network.model.departamento.UbiModel
import com.summit.core.network.model.departamento.UbicacionModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryRepositoryImplTest  {
    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    private lateinit var firestore: FirebaseFirestore

    @MockK(relaxed = true)
    private lateinit var db: UbicacionModelDao

    private val ubication = UbicacionModel("puno", "puno", 0)

    private val listCategorys= listOf(
        CategoriasModel("asdsad","todos","image.com",0),
        CategoriasModel("asdsaaaad","venta","image2.com",10)
    )
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
    private lateinit var cateRepo:CategoryRepositoryImpl
    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        cateRepo= CategoryRepositoryImpl(db,firestore)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(firestore, db)
        clearStaticMockk()
    }

    @Test
    fun `get all category, should return list data`()= runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        val querySnap= mockk<QuerySnapshot>()
        //GIVEN
            coEvery {
                firestore.collection("categorias").get().await()
            } returns querySnap
            coEvery {
                querySnap.toObjects(CategoriasModel::class.java)
            } returns listCategorys
        //WHEN
        val data=cateRepo.getAllCategorias()
        //THEN
        assertEquals(data,listCategorys)
        coVerifyOrder{
            firestore.collection("categorias").get().await()
            querySnap.toObjects(CategoriasModel::class.java)
        }
    }
    @Test
    fun `get all category, should return error` ()= runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        val querySnap= mockk<QuerySnapshot>()
        //GIVEN
        coEvery {
            firestore.collection("categorias").get().await()
        } throws  Exception("Error")
        coEvery {
            querySnap.toObjects(CategoriasModel::class.java)
        } returns listCategorys
        //WHEN
        val data=try{cateRepo.getAllCategorias()}catch (e:Exception){e}
        //THEN
        Truth.assertThat(data).isInstanceOf(Exception::class.java)
        coVerify(exactly = 1){
            firestore.collection("categorias").get().await()
        }
        coVerify(exactly = 0){
            querySnap.toObjects(CategoriasModel::class.java)
        }
    }
    @Test
    fun `Get getAnunciosByCategorias with limit 5, should return list data`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")

        val datoSnap= mockk<QuerySnapshot>()
        val id="ASdsdad"

        //GIVEN
        every { db.selectUbicacionModelStatic() } returns ubication

        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").whereEqualTo("typeID", id)
                .whereEqualTo("estado", "PUBLICADO").limit(5).get().await()
        } returns datoSnap

        coEvery { datoSnap.toObjects(Anuncios::class.java) } returns add
        // WHEN
        val response = cateRepo.getAnunciosByCategorias(id)
        // THEN
        Truth.assertThat(response).isEqualTo(add)
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").whereEqualTo("typeID", id)
                .whereEqualTo("estado", "PUBLICADO").limit(5).get().await()
        }
        verify(exactly = 1) { datoSnap.toObjects(Anuncios::class.java)!! }

    }
    @Test
    fun `Get getAnunciosByCategorias with limit 5 , should return Error`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")

        val datoSnap= mockk<QuerySnapshot>()
        val id="ASdsdad"

        //GIVEN
        every { db.selectUbicacionModelStatic() } returns ubication

        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").whereEqualTo("typeID", id)
                .whereEqualTo("estado", "PUBLICADO").limit(5).get().await()
        } throws  Exception("Error data")

        coEvery { datoSnap.toObjects(Anuncios::class.java) } returns add
        // WHEN
        val response =try{ cateRepo.getAnunciosByCategorias(id)}catch (e:Exception){e}
        // THEN
        Truth.assertThat(response).isInstanceOf(Exception::class.java)
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").whereEqualTo("typeID", id)
                .whereEqualTo("estado", "PUBLICADO").limit(5).get().await()
        }
        verify(exactly = 0) { datoSnap.toObjects(Anuncios::class.java) }

    }



    @Test
    fun `Get getAllAnunciosByCategorias , should return list data`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")

        val datoSnap= mockk<QuerySnapshot>()
        val id="ASdsdad"

        //GIVEN
        every { db.selectUbicacionModelStatic() } returns ubication

        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").whereEqualTo("typeID", id)
                .whereEqualTo("estado", "PUBLICADO").get().await()
        } returns datoSnap

        coEvery { datoSnap.toObjects(Anuncios::class.java) } returns add
        // WHEN
        val response = cateRepo.getAllAnunciosByCategorias(id)
        // THEN
        Truth.assertThat(response).isEqualTo(add)
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").whereEqualTo("typeID", id)
                .whereEqualTo("estado", "PUBLICADO").get().await()
        }
        verify(exactly = 1) { datoSnap.toObjects(Anuncios::class.java)!! }

    }
    @Test
    fun `Get getAllAnunciosByCategorias , should return Error`() = runBlockingTest {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")

        val datoSnap= mockk<QuerySnapshot>()
        val id="ASdsdad"

        //GIVEN
        every { db.selectUbicacionModelStatic() } returns ubication

        coEvery {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").whereEqualTo("typeID", id)
                .whereEqualTo("estado", "PUBLICADO").get().await()
        } throws  Exception("Error data")

        coEvery { datoSnap.toObjects(Anuncios::class.java) } returns add
        // WHEN
        val response =try{ cateRepo.getAllAnunciosByCategorias(id)}catch (e:Exception){e}
        // THEN
        Truth.assertThat(response).isInstanceOf(Exception::class.java)
        coVerify(exactly = 1) { db.selectUbicacionModelStatic() }
        coVerify(exactly = 1) {
            firestore.collection(ubication.departamento)
                .document(ubication.provincia).collection("anuncios").whereEqualTo("typeID", id)
                .whereEqualTo("estado", "PUBLICADO").get().await()
        }
        verify(exactly = 0) { datoSnap.toObjects(Anuncios::class.java) }

    }
}