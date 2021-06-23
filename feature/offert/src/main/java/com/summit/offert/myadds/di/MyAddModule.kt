package com.summit.offert.myadds.di

import androidx.annotation.VisibleForTesting
import com.summit.core.di.scope.FeatureScope
import dagger.Module
import dagger.Provides
import com.summit.commons.ui.extension.viewModel
import com.summit.core.network.repository.AdRepository
import com.summit.core.network.repository.PostulateRepository
import com.summit.core.network.repository.UserRepository
import com.summit.offert.myadds.MyAddViewModel
import com.summit.offert.myadds.MyAddsFragment

@Module
class MyAddModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment:MyAddsFragment
) {

    @FeatureScope
    @Provides
    fun setProviderViewModelPostulate(
      userRepo: UserRepository,
      adRepo: AdRepository
    ) = fragment.viewModel{
        MyAddViewModel(userRepo= userRepo,adRepo=adRepo)
    }
}