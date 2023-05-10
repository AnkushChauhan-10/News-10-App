package com.example.news10.adapters


import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.news10.R
import com.example.news10.response.NetworkStatus
import java.util.*

@BindingAdapter("setURL")
fun ImageView.setURL(url:String?=null) {
    try {
        if(url!=null) {
            Glide.with(this.context)
                .load(url).fitCenter().placeholder(R.drawable.baseline_arrow_circle_down_24)
                .into(this)
        }else{
            Glide.with(this.context).load(R.drawable.baseline_broken_image_24).centerCrop().into(this)
        }
    }catch (e:Exception){

    }
}

@BindingAdapter("setVisible")
fun CardView.setVisible(temp:NetworkStatus){
    if(temp == NetworkStatus.Available) {
        val layoutParams = this.layoutParams
        layoutParams.height = 0
        this.layoutParams =layoutParams
    }else if(temp == NetworkStatus.Unavailable){
        val layoutParams = this.layoutParams
        layoutParams.height = 100
        this.layoutParams =layoutParams
    }
}

@BindingAdapter("setTime")
fun TextView.setTime(date:Long){
    val d1 = Date(date)
    val d2 = Date()
    val diff = d2.time - d1.time
    val d = diff/(1000)
    when(d){
        in 0..59->this.text =  (diff/1000).toString()+"sec"
        in 60..3599->this.text = (diff/(60*1000)).toString() + "min"
        in 3600..86400->this.text = (diff/(60*60*1000)).toString()+"hr"
        else -> this.text = (diff/(60*60*24*1000)).toString() + "d"
    }
}


