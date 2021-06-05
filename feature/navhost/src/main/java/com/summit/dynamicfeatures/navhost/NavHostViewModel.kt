package com.summit.dynamicfeatures.navhost

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.summit.core.network.model.Usuario
import com.summit.core.network.repository.UserRepository

val NAV_FRAGMENTS_ID = setOf(R.id.nav_home)
val MENU_FRAGMENT_ID = setOf(R.menu.menu, R.menu.menu_publisher, R.menu.menu_admin)

class NavHostViewModel(private val userRepo: UserRepository) : ViewModel() {
    val getUserData = userRepo.getUserTimeReal()
    fun deleteUser() = userRepo.deleteUser()
    fun getStaticDataUser() = userRepo.getUserStatic()


    private val _state = MutableLiveData<NavHostViewState>()



    val state: LiveData<NavHostViewState>
        get() = _state

    @DrawableRes
    fun getMenuActual(user: Usuario?):Int {
        return if (user != null) {
            if (user.admin!!) {
                MENU_FRAGMENT_ID.elementAt(2)
            } else {
                (MENU_FRAGMENT_ID.elementAt(1))
            }
        } else {
            (MENU_FRAGMENT_ID.elementAt(0))
        }
    }

    fun navigationControllerChanged(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (NAV_FRAGMENTS_ID.contains(destination.id)) {
                _state.postValue(NavHostViewState.NavigationScreen)
            } else {
                _state.postValue(NavHostViewState.FullScreen)
            }
        }
    }
}