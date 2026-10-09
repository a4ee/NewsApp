package ru.pozdnyakov.news.domain.usecase

import ru.pozdnyakov.news.domain.entity.Language
import ru.pozdnyakov.news.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(language: Language) {
        settingsRepository.updateLanguage(language)
    }
}