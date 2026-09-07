package ru.pozdnyakov.news.domain.usecase

import ru.pozdnyakov.news.domain.repository.NewsRepository
import javax.inject.Inject

class AddSubscriptionUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(topic: String) {
        repository.addSubscription(topic)
        repository.updateArticlesForTopic(topic)
    }
}