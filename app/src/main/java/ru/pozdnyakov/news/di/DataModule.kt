package ru.pozdnyakov.news.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import ru.pozdnyakov.news.data.local.NewsDao
import ru.pozdnyakov.news.data.local.NewsDb
import ru.pozdnyakov.news.data.remote.NewsApiService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideJson(): Json {
            return Json{
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
        }

        @Provides
        @Singleton
        fun provideConverterFactory(json: Json): Converter.Factory {
            return json.asConverterFactory("application/json".toMediaType())
        }


        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build()
        }

        @Provides
        @Singleton
        fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
            return retrofit.create(NewsApiService::class.java)
        }

        @Provides
        @Singleton
        fun provideNewsDb(
            @ApplicationContext context: Context
        ): NewsDb {
            return Room.databaseBuilder(
                context = context,
                klass = NewsDb::class.java,
                name = "news.db"
            ).fallbackToDestructiveMigration(true).build()
        }

        @Provides
        @Singleton
        fun provideNewsDao(newsDb: NewsDb): NewsDao {
            return newsDb.newsDao()
        }
    }
}