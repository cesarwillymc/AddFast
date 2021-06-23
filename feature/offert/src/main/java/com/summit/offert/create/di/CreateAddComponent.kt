package com.summit.offert.create.di

import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import com.summit.offert.create.CreateAddFragment
import dagger.Component

@FeatureScope
@Component(
    modules = [CreateAddModule::class],
    dependencies = [CoreComponent::class]
)
interface CreateAddComponent {
    fun inject(fragment:CreateAddFragment)
}