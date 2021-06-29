package com.summit.camerax.di

import com.summit.camerax.GalleryFragment
import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    modules = [GalleryModule::class],
    dependencies = [CoreComponent::class]
)
interface GalleryComponent{

    fun inject(fragment:GalleryFragment)
}