package com.summit.navhost

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.summit.core.network.model.Usuario
import com.summit.core.network.repository.UserRepository

val NAV_FRAGMENTS_ID = setOf(R.id.home_fragment)
val MENU_FRAGMENT_ID = setOf(R.menu.menu, R.menu.menu_publisher, R.menu.menu_admin)

class NavHostViewModel(private val userRepo: UserRepository) : ViewModel() {
    val getUserData = userRepo.getUserTimeReal()
    fun deleteUser() = userRepo.deleteUser()
    fun getStaticDataUser() = userRepo.getUserStatic()


    private val _state = MutableLiveData<NavHostViewState>()

    private val _menu = MutableLiveData<@DrawableRes Int>()

    val menu: LiveData<Int>
        get() = _menu

    val state: LiveData<NavHostViewState>
        get() = _state

    fun getMenuActual(user: Usuario?) {
        if (user != null) {
            if (user.admin!!) {
                _menu.postValue(MENU_FRAGMENT_ID.elementAt(2))
            } else {
                _menu.postValue(MENU_FRAGMENT_ID.elementAt(1))
            }
        } else {
            _menu.postValue(MENU_FRAGMENT_ID.elementAt(0))
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