package com.summit.camerax.di

import androidx.annotation.VisibleForTesting
import com.summit.camerax.GalleryFragment
import com.summit.camerax.GalleryViewModel
import com.summit.core.di.scope.FeatureScope
import com.summit.commons.ui.extension.viewModel
import dagger.Module
import dagger.Provides

@Module
class GalleryModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment: GalleryFragment
) {
    @Provides
    @FeatureScope
    fun providesNavViewModel(
    ) = fragment.viewModel {
        GalleryViewModel()
    }
}