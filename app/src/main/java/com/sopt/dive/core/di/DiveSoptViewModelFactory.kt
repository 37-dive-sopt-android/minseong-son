package com.sopt.dive.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DiveSoptViewModelFactory(
    private val creators: Map<Class<out ViewModel>, () -> ViewModel>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass]
            ?: creators.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")

        @Suppress("UNCHECKED_CAST")
        return creator() as T
    }
}
