package ru.pozdnyakov.news.data.mapper

import ru.pozdnyakov.news.data.local.ArticleDbModel
import ru.pozdnyakov.news.data.remote.ArticleDto
import ru.pozdnyakov.news.data.remote.NewsResponseDto
import ru.pozdnyakov.news.domain.entity.Article
import ru.pozdnyakov.news.domain.entity.Interval
import ru.pozdnyakov.news.domain.entity.Language
import java.text.SimpleDateFormat
import java.util.Locale

fun NewsResponseDto.toDbModel(topic: String): List<ArticleDbModel> {
    return articles.map {
        ArticleDbModel(
            title = it.title,
            description = it.description,
            url = it.url,
            imageUrl = it.urlToImage,
            sourceName = it.source.name,
            topic = topic,
            publishedAt = it.publishedAt.toTimestamp()

        )
    }
}

fun Language.toQueryParam(): String {
    return when(this) {
        Language.ENGLISH -> "en"
        Language.RUSSIAN -> "ru"
        Language.FRENCH -> "fr"
        Language.GERMAN -> "de"
    }
}

fun Int.toInterval(): Interval {
    return Interval.entries.first{ it.minutes == this}
}

fun List<ArticleDbModel>.toEntities(): List<Article> {
    return map {
        Article(
            title = it.title,
            description = it.description,
            url = it.url,
            imageUrl = it.imageUrl,
            sourceName = it.sourceName,
            publishedAt = it.publishedAt
        )
    }.distinct()
}

private fun String.toTimestamp(): Long {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return dateFormatter.parse(this)?.time ?: System.currentTimeMillis()

}

