package com.summit.home.detail.di

import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import com.summit.home.detail.DetailAdFragment
import dagger.Component

@FeatureScope
@Component(
    modules = [DetailAdModule::class],
    dependencies = [CoreComponent::class]
)
interface DetailAdComponent {

    fun inject(fragment:DetailAdFragment)
}