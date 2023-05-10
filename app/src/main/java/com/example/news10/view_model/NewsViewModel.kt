package com.example.news10.view_model


import androidx.lifecycle.*
import com.example.news10.models.DaoNewsModel
import com.example.news10.response.ApiResponse
import com.example.news10.repository.NewsRepository
import com.example.news10.response.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository): ViewModel() {


    private val _networkStatus = MutableStateFlow<NetworkStatus>((NetworkStatus.Unavailable))
    val networkStatus = _networkStatus.asStateFlow()

    fun checkInternet(){
        viewModelScope.launch {
            repository.getNetwork().collect{
                _networkStatus.emit(it)
            }
        }
    }


    private val _sportsNewsStateFlow = MutableStateFlow<ApiResponse<List<DaoNewsModel>>>(ApiResponse.Loading())
    val sportsNewsStateFlow = _sportsNewsStateFlow.asStateFlow()

    private val _technologyNewsStateFlow = MutableStateFlow<ApiResponse<List<DaoNewsModel>>>(ApiResponse.Loading())
    val technologyNewsStateFlow = _technologyNewsStateFlow.asStateFlow()

    private val _businessNewsStateFlow = MutableStateFlow<ApiResponse<List<DaoNewsModel>>>(ApiResponse.Loading())
    val businessNewsStateFlow = _businessNewsStateFlow.asStateFlow()

    private val _entertainmentNewsStateFlow = MutableStateFlow<ApiResponse<List<DaoNewsModel>>>(ApiResponse.Loading())
    val entertainmentNewsStateFlow = _entertainmentNewsStateFlow.asStateFlow()

    private val _generalNewsStateFlow = MutableStateFlow<ApiResponse<List<DaoNewsModel>>>(ApiResponse.Loading())
    val generalNewsStateFlow = _generalNewsStateFlow.asStateFlow()

    private val _scienceNewsStateFlow = MutableStateFlow<ApiResponse<List<DaoNewsModel>>>(ApiResponse.Loading())
    val scienceNewsStateFlow = _scienceNewsStateFlow.asStateFlow()

    private val _healthNewsStateFlow = MutableStateFlow<ApiResponse<List<DaoNewsModel>>>(ApiResponse.Loading())
    val healthNewsStateFlow = _healthNewsStateFlow.asStateFlow()

    fun getNewFeed(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.newFeed(category)
        }
    }

    fun getNewsDao(category: String){
        viewModelScope.launch {
            repository.getFromDao(category).onStart {
                emit(ApiResponse.Loading())
            }.collect{
                when(category){
                    com.example.news10.utils.Constants.sportsFragment -> _sportsNewsStateFlow.emit(it)
                    com.example.news10.utils.Constants.technologyFragment -> _technologyNewsStateFlow.emit(it)
                    com.example.news10.utils.Constants.businessFragment -> _businessNewsStateFlow.emit(it)
                    com.example.news10.utils.Constants.entertainmentFragment -> _entertainmentNewsStateFlow.emit(it)
                    com.example.news10.utils.Constants.healthFragment -> _healthNewsStateFlow.emit(it)
                    com.example.news10.utils.Constants.scienceFragment -> _scienceNewsStateFlow.emit(it)
                    com.example.news10.utils.Constants.generalFragment -> _generalNewsStateFlow.emit(it)
                }
            }
        }
    }
}
class NewsViewModelFactory(private val repository: NewsRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }
}