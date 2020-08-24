package com.hkurbardovic.interactivereading.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hkurbardovic.interactivereading.main.repositories.MainRepository
import javax.inject.Inject

/**
 * Factory for creating a [MainViewModel] with a constructor that takes a [MainRepository].
 */
class MainViewModelFactory @Inject constructor(
    private val repository: MainRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(repository) as T
}