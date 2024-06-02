package com.superbeta.blibberly.home.main.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.superbeta.blibberly.home.main.domain.HomeRepository
import com.superbeta.blibberly.home.main.domain.HomeRepositoryImpl

@Suppress("UNCHECKED_CAST")
class HomeViewModel(val homeRepository: HomeRepository) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val homeRepo = HomeRepositoryImpl()
                return HomeViewModel(homeRepository = homeRepo) as T
            }
        }
    }

}