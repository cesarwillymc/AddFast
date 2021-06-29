
package com.summit.dynamicfeatures.navhost.dialog.di

import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.FeatureScope
import com.summit.dynamicfeatures.navhost.dialog.SelectPlaceDialog
import dagger.Component


@FeatureScope
@Component(
    modules = [DialogSelectPlaceModule::class],
    dependencies = [CoreComponent::class]
)
interface PlaceComponent {


    fun inject(placeDialog: SelectPlaceDialog)
}
