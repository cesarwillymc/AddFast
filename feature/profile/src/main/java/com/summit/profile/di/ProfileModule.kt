package com.summit.profile.di

import androidx.annotation.VisibleForTesting
import com.summit.commons.ui.extension.viewModel
import com.summit.core.di.scope.FeatureScope
import com.summit.core.network.repository.UserRepository
import com.summit.profile.ProfileFragment
import com.summit.profile.ProfileViewModel
import dagger.Module
import dagger.Provides

@Module
class ProfileModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment:ProfileFragment
) {
    @FeatureScope
    @Provides
    fun providesProfileFragment(
        userRepository: UserRepository
    )= fragment.viewModel {
        ProfileViewModel(userRepo = userRepository)
    }
}