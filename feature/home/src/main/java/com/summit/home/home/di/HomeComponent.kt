
package com.summit.home.home.di

import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import com.summit.home.home.HomeFragment
import dagger.Component


@FeatureScope
@Component(
    modules = [HomeModule::class],
    dependencies = [CoreComponent::class]
)
interface HomeComponent {


    fun inject(homeFragment: HomeFragment)
}
