package com.summit.core.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class SignInTest{
    @Test
    fun createSignIn_shouldCorrectDatainAttributes(){
        val code="2312"
        val codeRecibe="111111"
        val password="Data Store"
        val phone="962601310"
        val signin= SignIn(
            code = code,
            codeRecibe = codeRecibe,
            password = password,
            numberPhone = phone
        )

        Assertions.assertEquals(code ,signin.code)
        Assertions.assertEquals(codeRecibe, signin.codeRecibe)
        Assertions.assertEquals(password, signin.password)
        Assertions.assertEquals(phone, signin.numberPhone)

    }
}