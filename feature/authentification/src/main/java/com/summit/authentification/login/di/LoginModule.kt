package com.summit.authentification.login.di

import androidx.annotation.VisibleForTesting
import com.summit.authentification.login.LoginFragment
import com.summit.authentification.login.LoginViewModel
import com.summit.core.di.scope.FeatureScope
import com.summit.commons.ui.extension.viewModel
import dagger.Module
import dagger.Provides

@Module
class LoginModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment:LoginFragment
) {

    @FeatureScope
    @Provides
    fun setupFragmentWithViewModel() = fragment.viewModel{
        LoginViewModel()
    }
}