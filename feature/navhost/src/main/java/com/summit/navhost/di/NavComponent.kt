
package com.summit.navhost.di

import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import com.summit.navhost.nav.NavFragment
import dagger.Component


@FeatureScope
@Component(
    modules = [NavModule::class],
    dependencies = [CoreComponent::class]
)
interface NavComponent {


    fun inject(homeFragment: NavFragment)
}
