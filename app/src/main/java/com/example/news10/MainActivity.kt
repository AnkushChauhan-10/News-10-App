package com.example.news10


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.news10.adapters.NewsViewPagerAdapter
import com.example.news10.databinding.ActivityMainBinding
import com.example.news10.fragments.WebViewFragment
import com.example.news10.utils.Constants
import com.example.news10.view_model.NewsViewModel
import com.example.news10.view_model.NewsViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding

    private val fragmentList = arrayOf("General","Business","Entertainment","Health","Science","Sports","Technology")
    private val fragment_url = arrayOf(Constants.general_url,Constants.business_url,Constants.entertainment_url,
        Constants.health_url,Constants.science_url,Constants.sports_url,Constants.technology_url)
    private var position:Int = 0
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
        binding.url = Constants
        // ViewPager Set up
        setViewPager()

    }

    fun webPage(url:String){
        val bundle = Bundle()
        bundle.putString("url",url)
        binding.fg.visibility = View.GONE
        val webViewFragment = WebViewFragment()
        webViewFragment.arguments = bundle
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.fragment_container,webViewFragment,"fist")
        ft.addToBackStack(null)
        ft.commit()
    }
    private fun setViewPager(){
        val adapter = NewsViewPagerAdapter(supportFragmentManager,lifecycle,fragmentList)
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager2){tab,position ->
            val tb = LayoutInflater.from(applicationContext).inflate(R.layout.tab_layout,null)
            Glide.with(this).load(fragment_url.get(position)).override(160,160).into(tb.findViewById<ImageView>(R.id.img))
            tb.findViewById<TextView>(R.id.tabText).text = fragmentList.get(position)
            tab.setCustomView(tb)
            this.position = position
        }.attach()
    }

   fun onBackActivity(){
       binding.fg.visibility = View.VISIBLE
   }
}