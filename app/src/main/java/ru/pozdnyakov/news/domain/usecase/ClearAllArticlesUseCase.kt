package ru.pozdnyakov.news.domain.usecase

import ru.pozdnyakov.news.domain.repository.NewsRepository
import javax.inject.Inject

class ClearAllArticlesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(topics: List<String>) {
        repository.clearAllArticles(topics)
    }
}