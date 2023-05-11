package com.example.news10.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.news10.MainActivity
import com.example.news10.R
import com.example.news10.databinding.FragmentWebViewBinding

class WebViewFragment:Fragment(R.layout.fragment_web_view) {
    lateinit var binding: FragmentWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPress()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_web_view,container,false)
        webView(arguments!!.getString("url").toString())
        return binding.root
    }

    private fun webView(url: String){
        binding.webView.webViewClient  = WebViewClient()
        binding.webView.loadUrl(url)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
    }

    private fun onBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(this,object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(isEnabled){
                    if(binding.webView.canGoBack()) binding.webView.goBack()
                    else{
                        isEnabled = false
                        (requireActivity() as MainActivity).onBackActivity()
                        requireActivity().onBackPressed()
                    }
                }
            }
        })
    }

}