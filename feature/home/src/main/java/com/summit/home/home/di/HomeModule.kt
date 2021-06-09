package com.summit.home.home.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.summit.core.di.scope.FeatureScope
import com.summit.commons.ui.extension.viewModel
import com.summit.core.network.repository.CategoryRepository
import com.summit.core.network.repository.GpsRepository
import com.summit.core.network.repository.OfferRepository
import com.summit.home.home.HomeFragment
import com.summit.home.home.HomeViewModel
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
        repoUbi: GpsRepository,
        repoPromo: OfferRepository,
        repoCategory: CategoryRepository
    ) = fragment.viewModel {
        HomeViewModel(
            repoUbi = repoUbi,
            repoPromo = repoPromo,
            repoCategory = repoCategory
        )
    }
}
