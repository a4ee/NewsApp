package ru.pozdnyakov.news.domain.usecase

import ru.pozdnyakov.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetAllSubscriptionsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke() = repository.getAllSubscriptions()
}