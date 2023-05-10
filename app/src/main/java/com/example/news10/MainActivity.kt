package com.example.news10


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.news10.adapters.NewsViewPagerAdapter
import com.example.news10.databinding.ActivityMainBinding
import com.example.news10.view_model.NewsViewModel
import com.example.news10.view_model.NewsViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NewsViewModel
    private val fragmentList = arrayOf("General","Business","Entertainment","Health","Science","Sports","Technology")
    private var position:Int = 0
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        // View Model
        val repo = (application as NewsApplication).newsRepository
        viewModel = ViewModelProvider(this,NewsViewModelFactory(repo)).get(NewsViewModel::class.java)
        viewModel.checkInternet()
        // Data Binding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this //Life cycle for data binding object

        // ViewPager Set up
        setViewPager()

    }

    private fun setViewPager(){
        val adapter = NewsViewPagerAdapter(supportFragmentManager,lifecycle,fragmentList)
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager2){tab,position ->
            tab.text = fragmentList[position]
            this.position = position
        }.attach()
    }


}