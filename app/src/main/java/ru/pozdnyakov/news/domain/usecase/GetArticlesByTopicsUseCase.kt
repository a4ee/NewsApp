package ru.pozdnyakov.news.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.pozdnyakov.news.domain.entity.Article
import ru.pozdnyakov.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetArticlesByTopicsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(topics: List<String>): Flow<List<Article>> {
        return repository.getArticlesByTopics(topics)
    }
}