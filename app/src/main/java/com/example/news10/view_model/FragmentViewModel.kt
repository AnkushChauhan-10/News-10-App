package com.example.news10.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.news10.models.DaoNewsModel
import com.example.news10.repository.NewsRepository
import com.example.news10.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FragmentViewModel(private val repository: NewsRepository,private val category: String): ViewModel() {

    private val _newsStateFlow = MutableStateFlow<ApiResponse<List<DaoNewsModel>>>(ApiResponse.Loading())
    val newsStateFlow = _newsStateFlow.asStateFlow()

    fun getNewFeed(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.newFeed(category)
        }
    }

    fun getNewsDao(){
        viewModelScope.launch {
            repository.getFromDao(category).onStart {
                emit(ApiResponse.Loading())
            }.collect{
                _newsStateFlow.emit(it)
            }
        }
    }
}

class FragmentViewModelFactory(private val repository: NewsRepository,private val category: String): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FragmentViewModel(repository,category) as T
    }
}