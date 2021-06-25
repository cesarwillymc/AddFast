package com.summit.postulateadd.di

import androidx.annotation.VisibleForTesting
import com.summit.core.di.scope.FeatureScope
import dagger.Module
import dagger.Provides
import com.summit.commons.ui.extension.viewModel
import com.summit.core.network.repository.*
import com.summit.postulateadd.PostulateAddFragment
import com.summit.postulateadd.PostulateAddViewModel

@Module
class PostulateAddModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment: PostulateAddFragment
) {

    @FeatureScope
    @Provides
    fun setProviderViewModelPostulate(
        postulateRepo: PostulateRepository,
        userRepo: UserRepository,
    ) = fragment.viewModel {
        PostulateAddViewModel(postulateRepo = postulateRepo, userRepo = userRepo)
    }
}