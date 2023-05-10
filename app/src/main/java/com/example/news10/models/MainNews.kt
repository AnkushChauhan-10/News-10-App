package com.example.news10.models

data class MainNews(val status: String,
                    val totalResults: String,
                    val articles: ArrayList<DaoNewsModel>
                    )
