package ru.pozdnyakov.news.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.pozdnyakov.news.domain.entity.Article
import ru.pozdnyakov.news.domain.entity.Language
import ru.pozdnyakov.news.domain.entity.RefreshConfig

interface NewsRepository {

    fun getAllSubscriptions(): Flow<List<String>>

    suspend fun addSubscription(topic: String)

    suspend fun updateArticlesForTopic(topic: String, language: Language): Boolean

    suspend fun removeSubscription(topic: String)

    suspend fun updateArticlesForAllSubscriptions(language: Language): List<String>
    fun startBackgroundRefresh(refreshConfig: RefreshConfig)

    fun getArticlesByTopics(topics: List<String>): Flow<List<Article>>

    suspend fun clearAllArticles(topics: List<String>)
}