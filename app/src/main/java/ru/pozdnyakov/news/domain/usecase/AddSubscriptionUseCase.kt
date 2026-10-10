package ru.pozdnyakov.news.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.pozdnyakov.news.domain.entity.Language
import ru.pozdnyakov.news.domain.repository.NewsRepository
import ru.pozdnyakov.news.domain.repository.SettingsRepository
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class AddSubscriptionUseCase @Inject constructor(
    private val repository: NewsRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(topic: String) {
        repository.addSubscription(topic)
        CoroutineScope(coroutineContext).launch {
            repository.updateArticlesForTopic(topic, settingsRepository.getSettings().first().language)
        }
    }
}