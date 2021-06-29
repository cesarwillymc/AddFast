
package com.summit.core.di.modules

import com.summit.core.style.ThemeUtils
import com.summit.core.style.ThemeUtilsImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class UtilsModule {


    @Singleton
    @Binds
    abstract fun bindThemeUtils(themeUtilsImpl: ThemeUtilsImpl): ThemeUtils
}
