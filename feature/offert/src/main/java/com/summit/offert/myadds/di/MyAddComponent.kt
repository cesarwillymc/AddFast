package com.summit.offert.myadds.di

import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import com.summit.offert.myadds.MyAddsFragment
import dagger.Component

@FeatureScope
@Component(
    modules = [MyAddModule::class],
    dependencies = [CoreComponent::class]
)
interface MyAddComponent {
    fun inject(fragment:MyAddsFragment)
}