package com.summit.core.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.summit.core.network.model.departamento.UbicacionModel

@Dao
interface UbicacionModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUbicacionModel(ubicacionModel: UbicacionModel)

    @Update
    fun updateUbicacionModel(ubicacionModel: UbicacionModel)

    @Query("DELETE FROM NAME_TABLE_MAPEO")
    fun deleteUbicacionModel()

    @Query("SELECT * FROM NAME_TABLE_MAPEO")
    fun selectUbicacionModel(): LiveData<UbicacionModel>

    @Query("SELECT * FROM NAME_TABLE_MAPEO")
    fun selectUbicacionModelStatic(): UbicacionModel

}