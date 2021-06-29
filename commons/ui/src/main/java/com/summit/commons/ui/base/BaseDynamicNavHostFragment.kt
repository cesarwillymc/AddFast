package com.summit.commons.ui.base

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.fragment.app.activityViewModels
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.fragment.NavHostFragment
import com.summit.commons.ui.error.NavigationException
import com.summit.commons.ui.livedata.Event
import com.summit.commons.ui.viewmodel.NavControllerViewModel


class BaseDynamicNavHostFragment : DynamicNavHostFragment() {

    private val navControllerViewModel by activityViewModels<NavControllerViewModel>()

    override fun onResume() {
        super.onResume()
        // Set this navController as ViewModel's navController
        navControllerViewModel.currentNavController.value = Event(navController)
    }

    override fun onDestroyView() {
        navControllerViewModel.currentNavController.value = Event(null)
        super.onDestroyView()
    }

    companion object {

        private const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"
        private const val KEY_START_DESTINATION_ARGS =
            "android-support-nav:fragment:startDestinationArgs"

        /**
         * Create a new NavHostFragment instance with an inflated [NavGraph] resource.
         *
         * @param graphResId resource id of the navigation graph to inflate
         * @param startDestinationArgs arguments to send to the start destination of the graph
         * @return a new NavHostFragment instance
         */

        @JvmStatic
        fun createDynamicNavHostFragment(
            @NavigationRes graphResId: Int,
            startDestinationArgs: Bundle? = null
        ): BaseDynamicNavHostFragment {

            if (graphResId == 0) throw NavigationException("Navigation graph id cannot be 0")

            val bundle: Bundle = Bundle().apply {
                putInt(KEY_GRAPH_ID, graphResId)

                if (startDestinationArgs != null) {
                    putBundle(KEY_START_DESTINATION_ARGS, startDestinationArgs)
                }
            }

            return BaseDynamicNavHostFragment().apply {
                arguments = bundle
            }
        }
    }
}
