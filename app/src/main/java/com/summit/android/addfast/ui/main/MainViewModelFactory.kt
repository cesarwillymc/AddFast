package com.summit.android.addfast.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.summit.android.addfast.repo.conexion.MainRepository

class MainViewModelFactory(private val repo: MainRepository) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  MainViewModel(repo) as T
    }

}