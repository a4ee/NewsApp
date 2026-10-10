package ru.pozdnyakov.news.domain.usecase

import kotlinx.coroutines.flow.first
import ru.pozdnyakov.news.domain.repository.NewsRepository
import ru.pozdnyakov.news.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateSubscribedArticlesUseCase @Inject constructor(
    private val repository: NewsRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.updateArticlesForAllSubscriptions(settingsRepository.getSettings().first().language)
    }
}