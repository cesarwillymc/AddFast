package com.summit.dynamicfeatures.navhost.dialog.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.summit.core.di.scope.FeatureScope
import com.summit.core.network.repository.UserRepository
import com.summit.dynamicfeatures.navhost.NavHostViewModel
import com.summit.dynamicfeatures.navhost.nav.NavFragment
import com.summit.commons.ui.extension.viewModel
import com.summit.core.network.repository.GpsRepository
import com.summit.dynamicfeatures.navhost.dialog.SelectPlaceDialog
import dagger.Module
import dagger.Provides


@Module
class DialogSelectPlaceModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: SelectPlaceDialog
) {


    @Provides
    @FeatureScope
    fun providesNavViewModel(
    userRepository: UserRepository,
    ubiRepo : GpsRepository
    ) = fragment.viewModel {
        NavHostViewModel(
            userRepo =userRepository,
            ubiRepo = ubiRepo
        )
    }
}
