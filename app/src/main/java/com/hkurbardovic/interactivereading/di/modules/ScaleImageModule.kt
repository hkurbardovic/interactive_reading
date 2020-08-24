package com.hkurbardovic.interactivereading.di.modules

import android.content.Context
import com.hkurbardovic.interactivereading.app.InteractiveReading
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ScaleImageModule {

    @Provides
    @Singleton
    fun provideContext(app: InteractiveReading): Context {
        return app.applicationContext
    }
}