package com.summit.commons.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.summit.commons.ui.livedata.Event

class NavControllerViewModel : ViewModel() {
    val currentNavController = MutableLiveData<Event<NavController?>>()
}
