package com.hkurbardovic.interactivereading.di

import com.hkurbardovic.interactivereading.app.InteractiveReading
import com.hkurbardovic.interactivereading.di.modules.ActivityBindingModule
import com.hkurbardovic.interactivereading.di.modules.ContextModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ContextModule::class
    ]
)
interface AppComponent : AndroidInjector<InteractiveReading> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: InteractiveReading): AppComponent
    }
}