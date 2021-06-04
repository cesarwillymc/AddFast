/*
package com.summit.android.addfast.repo.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.utils.Constants

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsuario(usuario: Usuario)
    @Update
    fun updateUsuario(usuario: Usuario)
    @Query("DELETE FROM ${Constants.NAME_TABLE_USER}")
    fun deleteUsuario()
    @Query("SELECT * FROM ${Constants.NAME_TABLE_USER}" )
    fun selectUsuario():LiveData<Usuario>
    @Query("SELECT * FROM ${Constants.NAME_TABLE_USER}" )
    fun selectUsuarioStatic():Usuario
}
 */