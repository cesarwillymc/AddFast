package com.summit.core.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.summit.core.network.model.Usuario

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsuario(usuario: Usuario)
    @Update
    fun updateUsuario(usuario: Usuario)
    @Query("DELETE FROM NAME_TABLE_USER")
    fun deleteUsuario()
    @Query("SELECT * FROM NAME_TABLE_USER" )
    fun selectUsuario():LiveData<Usuario>
    @Query("SELECT * FROM NAME_TABLE_USER" )
    fun selectUsuarioStatic():Usuario
}