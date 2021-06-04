/*
package com.summit.android.addfast.repo.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.utils.Constants

@Dao
interface UbicacionModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUbicacionModel(ubicacionModel: UbicacionModel)
    @Update
    fun updateUbicacionModel(ubicacionModel: UbicacionModel)
    @Query("DELETE FROM ${Constants.NAME_TABLE_MAPEO}")
    fun deleteUbicacionModel()
    @Query("SELECT * FROM ${Constants.NAME_TABLE_MAPEO}" )
    fun selectUbicacionModel():LiveData<UbicacionModel>
    @Query("SELECT * FROM ${Constants.NAME_TABLE_MAPEO}" )
    fun selectUbicacionModelStatic():UbicacionModel
}
 */