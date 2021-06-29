package com.summit.profile.di

import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import com.summit.profile.ProfileFragment
import dagger.Component
@FeatureScope
@Component(
    modules = [ProfileModule::class],
    dependencies = [CoreComponent::class]
)
interface ProfileComponent {

    fun inject(fragment:ProfileFragment)
}