package ru.pozdnyakov.news.data.mapper

import ru.pozdnyakov.news.domain.entity.RefreshConfig
import ru.pozdnyakov.news.domain.entity.Settings

fun Settings.toRefreshConfig(): RefreshConfig {
    return RefreshConfig(language, interval, wifiOnly)
}