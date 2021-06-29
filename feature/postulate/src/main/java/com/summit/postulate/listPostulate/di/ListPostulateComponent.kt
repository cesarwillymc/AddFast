package com.summit.postulate.listPostulate.di

import com.summit.postulate.listPostulate.PostulateFragment
import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    modules = [ListPostulateModule::class],
    dependencies = [CoreComponent::class]
)
interface ListPostulateComponent {
    fun inject(fragment:PostulateFragment)
}