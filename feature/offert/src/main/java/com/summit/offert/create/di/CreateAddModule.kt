package com.summit.offert.create.di

import androidx.annotation.VisibleForTesting
import com.summit.core.di.scope.FeatureScope
import dagger.Module
import dagger.Provides
import com.summit.commons.ui.extension.viewModel
import com.summit.core.network.repository.AdRepository
import com.summit.offert.create.CreateAddFragment
import com.summit.offert.create.CreateAddViewModel

@Module
class CreateAddModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment:CreateAddFragment
) {

    @FeatureScope
    @Provides
    fun setProviderViewModelPostulate(
      adRepo: AdRepository
    ) = fragment.viewModel{
        CreateAddViewModel(adRepo=adRepo)
    }
}