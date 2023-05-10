package com.example.news10.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.news10.R
import com.example.news10.databinding.FragmentBusinessBinding
import com.example.news10.utils.Constants
import com.example.news10.FragmentsHelper
import com.example.news10.view_model.NewsViewModel

class BusinessFragment:Fragment() {

    private lateinit var binding: FragmentBusinessBinding
    private lateinit var viewModel: NewsViewModel
    private val fragmentName = Constants.businessFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_business,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        lifecycle.addObserver(
            FragmentsHelper(this.requireContext(),
            lifecycleScope,
            viewModel,
            viewModel.businessNewsStateFlow,
            binding.recycleView,
            binding.swipe,
            binding.progressCircular,
           fragmentName)
        )
    }

    override fun onPause() {
        super.onPause()
        Log.d("Fragmetn","Pau")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Fragmetn","rau")
    }

}