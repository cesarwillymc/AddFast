package com.summit.authentification.confirm.di

import com.summit.authentification.confirm.ConfirmCodeFragment
import com.summit.authentification.login.LoginFragment
import com.summit.authentification.login.di.LoginModule
import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import dagger.Component


@FeatureScope
@Component(
    modules = [ConfirmCodeModule::class],
    dependencies = [CoreComponent::class]
)
interface ConfirmCodeComponent {

    fun inject(fragment: ConfirmCodeFragment)

}