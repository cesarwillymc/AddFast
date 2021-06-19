package com.summit.authentification.confirm.di

import androidx.annotation.VisibleForTesting
import com.summit.authentification.confirm.ConfirmCodeFragment
import com.summit.authentification.confirm.ConfirmCodeViewModel
import com.summit.authentification.login.LoginViewModel
import com.summit.commons.ui.extension.viewModel
import com.summit.core.di.scope.FeatureScope
import dagger.Module
import dagger.Provides


@Module
class ConfirmCodeModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment:ConfirmCodeFragment
) {

    @FeatureScope
    @Provides
    fun setupFragmentWithViewModel() = fragment.viewModel{
        ConfirmCodeViewModel()
    }

}