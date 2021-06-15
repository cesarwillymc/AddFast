package com.summit.authentification.login.di

import com.summit.authentification.login.LoginFragment
import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    modules = [LoginModule::class],
    dependencies = [CoreComponent::class]
)
interface LoginComponent {
    fun inject(fragment:LoginFragment)
}