package com.summit.home.detail.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.summit.core.di.scope.FeatureScope
import com.summit.home.detail.DetailAdFragment
import com.summit.home.detail.DetailAdViewModel
import com.summit.commons.ui.extension.viewModel
import com.summit.core.network.repository.AdRepository
import com.summit.core.network.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module
class DetailAdModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: DetailAdFragment
) {
    @Provides
    @FeatureScope
    fun providesNavViewModel(
        adRepo: AdRepository,
        userRepo: UserRepository
    ) = fragment.viewModel {
        DetailAdViewModel(adRepo, userRepo)
    }

}