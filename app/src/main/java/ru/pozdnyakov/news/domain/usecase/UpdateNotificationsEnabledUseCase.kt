package ru.pozdnyakov.news.domain.usecase

import ru.pozdnyakov.news.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateNotificationsEnabledUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(enabled: Boolean) {
        settingsRepository.updateNotificationsEnabled(enabled)
    }
}