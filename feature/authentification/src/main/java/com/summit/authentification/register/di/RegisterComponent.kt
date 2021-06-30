package com.summit.authentification.register.di

import com.summit.authentification.register.RegisterFragment
import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    modules = [RegisterModule::class],
    dependencies = [CoreComponent::class]
)
interface RegisterComponent {

    fun inject(fragment:RegisterFragment)

}