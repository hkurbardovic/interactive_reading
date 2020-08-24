package com.hkurbardovic.interactivereading.di.modules

import com.hkurbardovic.interactivereading.di.scopes.FragmentScoped
import com.hkurbardovic.interactivereading.main.PageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
internal abstract class PageModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun pageFragment(): PageFragment
}