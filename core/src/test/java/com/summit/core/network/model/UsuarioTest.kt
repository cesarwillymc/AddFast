package com.summit.core.network.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UsuarioTest{


    @Test
    fun createUser_shouldCorrectDatainAttributes(){
        val name="Cesar"
        val dni="70321701"
        val nameEmpresa="Data Store"
        val phone="962601310"
        val ruc="10703121801"
        val uriImgPerfil="www.google.com"
        val reportes=10
        val accoutActive=true
        val _id="2993x012"
        val admin=false
        val usuario= Usuario(
            name = name,
            dni = dni,
            nameEmpresa = nameEmpresa,
            phone =phone ,
            ruc =ruc ,
            uriImgPerfil =uriImgPerfil ,
            reportes =reportes ,
            accountactivate = accoutActive,
            _id =_id ,
            admin =admin
        )

        assertEquals(name,usuario.name)
        assertEquals(dni,usuario.dni)
        assertEquals(nameEmpresa,usuario.nameEmpresa)
        assertEquals(phone,usuario.phone)
        assertEquals(ruc,usuario.ruc)
        assertEquals(uriImgPerfil,usuario.uriImgPerfil)
        assertEquals(reportes,usuario.reportes)
        assertEquals(accoutActive,usuario.accountactivate)
        assertEquals(_id,usuario._id)
        assertEquals(admin,usuario.admin)

    }
}