package com.summit.core.network.model

data class SignIn(
     val code:String,
     val numberPhone:String,
     val password:String = "th3lastford4ad",
     val codeRecibe:String
)
