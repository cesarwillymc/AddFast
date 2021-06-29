package com.summit.dynamicfeatures.navhost.nav.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.summit.core.di.scope.FeatureScope
import com.summit.core.network.repository.UserRepository
import com.summit.dynamicfeatures.navhost.NavHostViewModel
import com.summit.dynamicfeatures.navhost.nav.NavFragment
import com.summit.commons.ui.extension.viewModel
import com.summit.core.network.repository.GpsRepository
import dagger.Module
import dagger.Provides


@Module
class NavModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: NavFragment
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
