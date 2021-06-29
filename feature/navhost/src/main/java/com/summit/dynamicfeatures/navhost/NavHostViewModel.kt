package com.summit.dynamicfeatures.navhost

import android.annotation.SuppressLint
import android.view.Menu
import androidx.core.view.get
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.summit.core.network.model.departamento.ProvinciaItem
import com.summit.core.network.model.departamento.UbicacionModel
import com.summit.core.network.repository.GpsRepository
import com.summit.core.network.repository.UserRepository
import kotlinx.coroutines.Dispatchers

val NAV_FRAGMENTS_ID_BOTTOM = setOf("Home", "Profile", "Postulate", "MyAdds")
val NAV_FRAGMENTS_ID_NOT_APPBAR = setOf("GalleryFragment")


class NavHostViewModel(private val userRepo: UserRepository, private val ubiRepo: GpsRepository) : ViewModel() {
    val getUserData = userRepo.getUserTimeReal()


    /** UBICACION UPDATE **/
    fun updateUbicacionAppDb(item: UbicacionModel) = ubiRepo.updateUbicacionAppDb(item)
    fun saveUbicacion(item: UbicacionModel) = ubiRepo.saveUbicacion(item)
    val getUbicacion = ubiRepo.getUbicacion()
    fun getDepartamentos() = liveData<List<ProvinciaItem>>(viewModelScope.coroutineContext + Dispatchers.Main) {
        try {
            val resultado = ubiRepo.verDepartamento()
            if (resultado != null) {
                emit(resultado)
            } else {
                emit(listOf())
            }
        } catch (e: Exception) {
        }
    }

    fun getProvincias(id: String) = liveData<List<ProvinciaItem>>(viewModelScope.coroutineContext + Dispatchers.Main) {
        try {
            val resultado = ubiRepo.verProvincia(id)
            if (resultado != null) {
                emit(resultado)
            } else {
                emit(listOf())
            }
        } catch (e: Exception) {
        }
    }

    private val _state = MutableLiveData<NavHostViewState>()


    val state: LiveData<NavHostViewState>
        get() = _state



    fun setVisibilityMenu(menu: Menu) {

        val user = userRepo.getUserStatic()
        if (user != null) {
            when {
                user.ruc.isNullOrEmpty() -> {
                    menu[2].isVisible = false
                    menu[1].isVisible = true
                }
                else -> {
                    menu[1].isVisible = true
                    menu[2].isVisible = true
                }
            }
        } else {
            menu[1].isVisible = false
            menu[2].isVisible = false
        }
    }

    @SuppressLint("RestrictedApi")
    fun navigationControllerChanged(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (NAV_FRAGMENTS_ID_BOTTOM.contains(destination.label) && !NAV_FRAGMENTS_ID_NOT_APPBAR.contains(destination.label)) {
                _state.postValue(NavHostViewState.FullScreen)

            } else {
                when {
                    NAV_FRAGMENTS_ID_BOTTOM.contains(destination.label) -> {
                        _state.postValue(NavHostViewState.NavigationScreen)
                    }
                    !NAV_FRAGMENTS_ID_NOT_APPBAR.contains(destination.label) -> {
                        _state.postValue(NavHostViewState.AppBarScreen)
                    }
                    else -> {
                        _state.postValue(NavHostViewState.EmptyScreen)
                    }
                }

            }
        }
    }
}