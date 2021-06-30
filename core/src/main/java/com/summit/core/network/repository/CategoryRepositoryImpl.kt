package com.summit.core.network.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.network.model.Anuncios
import com.summit.core.network.model.CategoriasModel
import kotlinx.coroutines.tasks.await


internal class CategoryRepositoryImpl(
    private val db: UbicacionModelDao, private val firestore: FirebaseFirestore,

    ) : CategoryRepository {
    override suspend fun getAllCategorias(): List<CategoriasModel> {
        val categorias = firestore.collection("categorias").get().await()
        return categorias.toObjects(CategoriasModel::class.java)
    }

    override suspend fun getAnunciosByCategorias(id: String): List<Anuncios> {
        val ubicacion = db.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").whereEqualTo("typeID", id)
            .whereEqualTo("estado", "PUBLICADO").limit(5).get().await()

        return anuncios.toObjects(Anuncios::class.java)
    }

    override suspend fun getAllAnunciosByCategorias(id: String): List<Anuncios> {
        val ubicacion = db.selectUbicacionModelStatic()
        val anuncios = firestore.collection(ubicacion.departamento.trim().toLowerCase())
            .document(ubicacion.provincia.trim().toLowerCase()).collection("anuncios").whereEqualTo("typeID", id)
            .whereEqualTo("estado", "PUBLICADO").get().await()
        return anuncios.toObjects(Anuncios::class.java)
    }


}