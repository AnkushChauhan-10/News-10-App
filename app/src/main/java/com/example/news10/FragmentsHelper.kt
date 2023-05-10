package com.example.news10

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.news10.adapters.NewsLayoutAdapter
import com.example.news10.models.DaoNewsModel
import com.example.news10.response.ApiResponse
import com.example.news10.view_model.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import java.util.*

class FragmentsHelper(
    val context: Context,
    val lifecycle: LifecycleCoroutineScope,
    val viewModel: NewsViewModel,
    val newsFlow: StateFlow<ApiResponse<List<DaoNewsModel>>>,
    val recycleView: RecyclerView,
    val swipe: SwipeRefreshLayout,
    val loading: ProgressBar,
    val fragment: String):LifecycleObserver {

    private lateinit var adapter: NewsLayoutAdapter

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
        recycleView.layoutManager = LinearLayoutManager(this.context)
        adapter = NewsLayoutAdapter()
        recycleView.adapter = adapter
        viewModel.getNewsDao(fragment)
        viewModel.getNewFeed(fragment)
        startFlow()
        setSwipe()
        Log.d("Observer","obsere")
    }


    fun setSwipe(){
        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
        }
    }
    fun startFlow(){
        lifecycle.launchWhenCreated{
            newsFlow.collectLatest {
                when(it){
                    is ApiResponse.Loading->{
                        loading.visibility = View.VISIBLE
                        Log.d("Loading","Loa-----------------------")
                    }
                    is ApiResponse.Success->{
                        recycleView.scrollToPosition(0)
                        loading.visibility = View.GONE
                        adapter.submitList(it.data)
                        Log.d("Susscu",it.toString())
                    }
                    is ApiResponse.Error->{
                        loading.visibility = View.GONE
                        Log.d("Error",it.errorMessage.toString())
                    }
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start(){
        Log.d("Event","Start")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume(){
        Log.d("Event","Resume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause(){
        Log.d("Event","Pause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop(){
        Log.d("Event","Stop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy(){
        Log.d("Event","Destroy")
    }
}