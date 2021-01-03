package com.summit.android.addfast.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.summit.android.addfast.repo.conexion.AuthRepository

class AuthViewModelFactory(private val repo:AuthRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }

}