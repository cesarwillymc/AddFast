package com.summit.authentification.register.di

import androidx.annotation.VisibleForTesting
import com.summit.authentification.register.RegisterFragment
import com.summit.authentification.register.RegisterViewModel
import com.summit.commons.ui.extension.viewModel
import com.summit.core.di.scope.FeatureScope
import com.summit.core.network.repository.AuthRepository
import com.summit.core.network.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module
class RegisterModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment:RegisterFragment
) {
    @FeatureScope
    @Provides
    fun setupFragmentWithViewModel(
        repoAuth: AuthRepository,
        repoUser: UserRepository
    ) = fragment.viewModel {
        RegisterViewModel(repoAuth = repoAuth,repoUser = repoUser)
    }
}