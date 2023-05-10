package com.example.news10.fragments



import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.news10.R
import com.example.news10.databinding.FragmentGeneralBinding
import com.example.news10.utils.Constants
import com.example.news10.FragmentsHelper
import com.example.news10.NewsApplication
import com.example.news10.view_model.FragmentViewModel
import com.example.news10.view_model.FragmentViewModelFactory
import com.example.news10.view_model.NewsViewModel

class GeneralFragment:Fragment() {

    private lateinit var binding: FragmentGeneralBinding
    private val fragmentName = Constants.generalFragment
    private val viewModel: FragmentViewModel by activityViewModels {
        FragmentViewModelFactory(
            (requireContext().applicationContext as NewsApplication).newsRepository,
            Constants.generalFragment
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_general,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        val repo = (context as NewsApplication).newsRepository
//        val v:ViewModelProvider = ViewModelProviders.of(this)
        binding.lifecycleOwner = viewLifecycleOwner
//        lifecycle.addObserver(
//            FragmentsHelper(this.requireContext(),
//            lifecycleScope,
//            viewModel,
//            viewModel.newsStateFlow,
//            binding.recycleView,
//            binding.swipe,
//            binding.progressCircular,
//            fragmentName)
//        )
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