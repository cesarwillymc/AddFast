package com.summit.postulate.listPostulate.di

import androidx.annotation.VisibleForTesting
import com.summit.postulate.listPostulate.PostulateFragment
import com.summit.postulate.listPostulate.PostulateViewModel
import com.summit.core.di.scope.FeatureScope
import dagger.Module
import dagger.Provides
import com.summit.commons.ui.extension.viewModel
import com.summit.core.network.repository.UserRepository

@Module
class ListPostulateModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment:PostulateFragment
) {

    @FeatureScope
    @Provides
    fun setProviderViewModelPostulate(
        userRepository: UserRepository
    ) = fragment.viewModel{
        PostulateViewModel(userRepo = userRepository)
    }
}