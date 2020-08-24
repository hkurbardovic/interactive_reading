package com.hkurbardovic.interactivereading.di.modules

import com.hkurbardovic.interactivereading.books.BooksActivity
import com.hkurbardovic.interactivereading.di.scopes.ActivityScoped
import com.hkurbardovic.interactivereading.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun booksActivity(): BooksActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PageModule::class])
    internal abstract fun mainActivity(): MainActivity
}