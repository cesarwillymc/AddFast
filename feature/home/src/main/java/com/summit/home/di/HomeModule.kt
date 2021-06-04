package com.summit.home.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.summit.core.di.scope.FeatureScope
import com.summit.commons.ui.extension.viewModel
import com.summit.home.HomeFragment
import com.summit.home.HomeViewModel
import dagger.Module
import dagger.Provides


@Module
class HomeModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: HomeFragment
) {


    @Provides
    @FeatureScope
    fun providesNavViewModel(
    ) = fragment.viewModel {
        HomeViewModel(
        )
    }
}
