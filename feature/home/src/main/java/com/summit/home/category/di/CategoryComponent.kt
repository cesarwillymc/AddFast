package com.summit.home.category.di

import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import com.summit.home.category.CategoryFragment
import dagger.Component

@FeatureScope
@Component(
    modules = [CategoryModule::class],
    dependencies = [CoreComponent::class]
)
interface CategoryComponent {
    fun inject(fragment:CategoryFragment)
}