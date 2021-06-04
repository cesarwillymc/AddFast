package com.summit.navhost.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.summit.core.di.scope.FeatureScope
import com.summit.core.network.repository.UserRepository
import com.summit.navhost.NavHostViewModel
import com.summit.navhost.nav.NavFragment
import com.summit.commons.ui.extension.viewModel
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
    userRepository: UserRepository
    ) = fragment.viewModel {
        NavHostViewModel(
            userRepo =userRepository
        )
    }
}
