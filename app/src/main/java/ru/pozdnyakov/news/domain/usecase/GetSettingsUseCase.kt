package ru.pozdnyakov.news.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.pozdnyakov.news.domain.entity.Settings
import ru.pozdnyakov.news.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Settings> {
        return settingsRepository.getSettings()
    }
}