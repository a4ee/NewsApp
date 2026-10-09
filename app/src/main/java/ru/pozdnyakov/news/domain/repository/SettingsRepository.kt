package ru.pozdnyakov.news.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.pozdnyakov.news.domain.entity.Language
import ru.pozdnyakov.news.domain.entity.Settings

interface SettingsRepository {

    fun getSettings(): Flow<Settings>

    suspend fun updateLanguage(language: Language)

    suspend fun updateInterval(minutes: Int)

    suspend fun updateNotificationsEnabled(enabled: Boolean)

    suspend fun updateWifiOnly(wifiOnly: Boolean)


}