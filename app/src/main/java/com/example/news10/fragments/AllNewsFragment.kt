package com.example.news10.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news10.MainActivity
import com.example.news10.R
import com.example.news10.adapters.NewsLayoutAdapter
import com.example.news10.databinding.FragmentAllNewsBinding
import com.example.news10.models.DaoNewsModel
import com.example.news10.response.ApiResponse
import com.example.news10.view_model.NewsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest

class AllNewsFragment(private val fragment: String):Fragment(){

    private lateinit var binding: FragmentAllNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsLayoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_news,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recycleView.layoutManager = LinearLayoutManager(this.context)
        adapter = NewsLayoutAdapter()
        binding.recycleView.adapter = adapter
        adapterClickListener()
        collectFlow()
        onSwipe()
        viewModel.getNews(fragment)
        refreshFeed()
    }


    fun onSwipe(){
        binding.swipe.setOnRefreshListener {
            refreshFeed()
        }
    }
    private fun refreshFeed(){
        binding.swipe.isRefreshing = true
        GlobalScope.async {
            viewModel.refreshFeed(fragment)
            binding.swipe.isRefreshing = false
        }
    }
    fun collectFlow(){
        lifecycleScope.launchWhenCreated{
            viewModel.getStateFlow(fragment).collectLatest {
                when(it){
                    is ApiResponse.Loading->{
                        onLoading()
                        Log.d("Loading","Fragment Helper Loading in fragment ${fragment} line no. 71")
                    }
                    is ApiResponse.Success->{
                        onSuccess(it)
                        Log.d("Success","Fragment Helper Success in Fragment ${fragment} line no. 75 data = "+it.data.toString())
                    }
                    is ApiResponse.Error->{
                        onError(it.errorMessage.toString())
                        Log.d("Error","Fragment Helper line 79 fragment ${fragment}, error = ${it}")
                    }
                }
            }
        }
    }

    private fun onLoading(){
        binding.errorMessage.visibility = View.GONE
        if(binding.swipe.isRefreshing == false)
            binding.progressCircular.visibility = View.VISIBLE
    }

    private fun onSuccess(it: ApiResponse<List<DaoNewsModel>>){
        try {
            adapter.submitList(it.data)
            binding.recycleView.layoutManager?.scrollToPosition(0)
            it.data?.get(1)?.let { it1 -> Log.d("des", it1.description) }
            binding.progressCircular.visibility = View.GONE
        }catch (e:Exception){

        }
    }

    private fun onError(it: String){
        Log.d("Error","Fragment Helper line 103 fragment ${fragment}, error = ${it}")
        binding.progressCircular.visibility = View.GONE
        binding.errorMessage.text = it
        binding.errorMessage.visibility = View.VISIBLE
    }

    private fun adapterClickListener(){
        adapter.setOnClick(object :NewsLayoutAdapter.OnClickListener{
            override fun onClickReadMore(url: String) {
                (activity as MainActivity).webPage(url)
            }

            override fun onClickShare(url: String) {
                shareUrl(url,"News 10")
            }
        })
    }

    fun shareUrl(url: String,title: String){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,url)
            putExtra(Intent.EXTRA_TITLE,title)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent,null)
        requireActivity().startActivity(shareIntent)
    }

}