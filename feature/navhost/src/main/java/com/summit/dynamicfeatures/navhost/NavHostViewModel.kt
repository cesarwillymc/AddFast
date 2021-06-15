package com.summit.dynamicfeatures.navhost

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.navigation.NavController
import com.summit.core.network.model.Usuario
import com.summit.core.network.model.departamento.ProvinciaItem
import com.summit.core.network.model.departamento.UbicacionModel
import com.summit.core.network.repository.GpsRepository
import com.summit.core.network.repository.UserRepository

val NAV_FRAGMENTS_ID_BOTTOM = setOf(R.id.nav_home,R.id.nav_profile,R.id.nav_postulate)
val NAV_FRAGMENTS_ID_NOT_APPBAR = setOf<@DrawableRes Int>()
val MENU_FRAGMENT_ID = setOf(R.menu.menu,R.menu.menu_user, R.menu.menu_publisher, R.menu.menu_admin)

class NavHostViewModel(private val userRepo: UserRepository, private val ubiRepo: GpsRepository) : ViewModel() {
    val getUserData = userRepo.getUserTimeReal()
    fun deleteUser() = userRepo.deleteUser()
    fun getStaticDataUser() = userRepo.getUserStatic()


    /** UBICACION UPDATE **/
    fun updateUbicacionAppDb(item: UbicacionModel) = ubiRepo.updateUbicacionAppDb(item)
    fun saveUbicacion(item: UbicacionModel) = ubiRepo.saveUbicacion(item)
    val getUbicacion = ubiRepo.getUbicacion()
    fun getDepartamentos() = liveData<List<ProvinciaItem>> {
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

    fun getProvincias(id: String) = liveData<List<ProvinciaItem>> {
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

    @DrawableRes
    fun getMenuActual(user: Usuario?): Int {
        return if (user != null) {
            when {
                user.admin!! -> {
                    MENU_FRAGMENT_ID.elementAt(3)
                }
                user.ruc.isNullOrEmpty() -> {
                    (MENU_FRAGMENT_ID.elementAt(1))
                }
                else -> {
                    (MENU_FRAGMENT_ID.elementAt(2))
                }
            }
        } else {
            (MENU_FRAGMENT_ID.elementAt(0))
        }
    }

    fun navigationControllerChanged(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (NAV_FRAGMENTS_ID_BOTTOM.contains(destination.id) && !NAV_FRAGMENTS_ID_NOT_APPBAR.contains(destination.id)) {
                _state.postValue(NavHostViewState.FullScreen)
            } else {
                when {
                    NAV_FRAGMENTS_ID_BOTTOM.contains(destination.id) -> {
                        _state.postValue(NavHostViewState.NavigationScreen)
                    }
                    !NAV_FRAGMENTS_ID_NOT_APPBAR.contains(destination.id) -> {
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