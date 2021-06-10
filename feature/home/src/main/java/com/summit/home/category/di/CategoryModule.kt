package com.summit.home.category.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.summit.core.di.scope.FeatureScope
import com.summit.home.category.CategoryFragment
import com.summit.home.category.CategoryViewModel
import dagger.Module
import dagger.Provides
import com.summit.commons.ui.extension.viewModel
import com.summit.core.network.repository.CategoryRepository

@Module
class CategoryModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: CategoryFragment
) {
    @Provides
    @FeatureScope
    fun providesNavViewModel(
        repoCate:CategoryRepository
    ) = fragment.viewModel {
        CategoryViewModel(
            repoCate=repoCate
        )
    }
}