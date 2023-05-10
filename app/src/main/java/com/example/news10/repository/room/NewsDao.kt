package com.example.news10.repository.room

import androidx.room.*
import com.example.news10.models.DaoNewsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(newsModelList: List<DaoNewsModel>)

    @Query("DELETE FROM news_table WHERE news_type = :category")
    suspend fun deleteAll(category: String)

    @Query("SELECT * FROM news_table WHERE news_type = :category ORDER BY date DESC")
    fun getNews(category: String): Flow<List<DaoNewsModel>>
}