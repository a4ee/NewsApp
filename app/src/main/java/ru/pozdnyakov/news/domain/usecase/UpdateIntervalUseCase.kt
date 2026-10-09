package ru.pozdnyakov.news.domain.usecase

import ru.pozdnyakov.news.domain.entity.Interval
import ru.pozdnyakov.news.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateIntervalUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(interval: Interval) {
        settingsRepository.updateInterval(interval.minutes)
    }
}