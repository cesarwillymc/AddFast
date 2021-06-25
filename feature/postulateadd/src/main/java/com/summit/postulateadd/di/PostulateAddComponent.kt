package com.summit.postulateadd.di

import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import com.summit.postulateadd.PostulateAddFragment
import dagger.Component

@FeatureScope
@Component(
    modules = [PostulateAddModule::class],
    dependencies = [CoreComponent::class]
)
interface PostulateAddComponent {
    fun inject(fragment:PostulateAddFragment)
}