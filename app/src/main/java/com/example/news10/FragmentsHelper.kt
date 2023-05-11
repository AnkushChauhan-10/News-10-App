package com.example.news10

import android.app.Activity
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.acos

class FragmentsHelper(
    val context: Context,
    val activity: Activity,
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
        adapterClickListener()
        recycleView.adapter = adapter
        refreshFeed()
        viewModel.getNews(fragment)
        collectFlow()
        onSwipe()
        Log.d("Observer","obsere")
    }


    fun onSwipe(){
        swipe.setOnRefreshListener {
           refreshFeed()
        }
    }
    private fun refreshFeed(){
        swipe.isRefreshing = true
        GlobalScope.async {
            viewModel.refreshFeed(fragment)
            swipe.isRefreshing = false
        }
    }
    fun collectFlow(){
        lifecycle.launchWhenCreated{
            newsFlow.collectLatest {
                when(it){
                    is ApiResponse.Loading->{
                        onLoading()
                        Log.d("Loading","Loa-----------------------")
                    }
                    is ApiResponse.Success->{
                        onSuccess(it)
                        Log.d("Susscu",it.toString())
                    }
                    is ApiResponse.Error->{
                        onError(it.errorMessage.toString())
                        Log.d("Error",it.errorMessage.toString())
                    }
                }
            }
        }
    }

    private fun onLoading(){
        if(swipe.isRefreshing == false)
            loading.visibility = View.VISIBLE
    }

    private fun onSuccess(it: ApiResponse<List<DaoNewsModel>>){
        recycleView.scrollToPosition(0)
        adapter.submitList(it.data)
        Log.d("des",it.data!!.get(1).description)
        loading.visibility = View.GONE
    }

    private fun onError(it: String){
        loading.visibility = View.GONE
    }

    private fun adapterClickListener(){
        adapter.setOnClick(object :NewsLayoutAdapter.OnClickListener{
            override fun onClickReadMore(url: String) {
                (activity as MainActivity).webPage(url)
            }
        })
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