
package com.summit.android.addfast.di

import com.summit.android.addfast.app.MyApp
import com.summit.core.di.CoreComponent
import com.summit.core.di.scope.AppScope
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {

    fun inject(application: MyApp)
}
