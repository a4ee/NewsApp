package ru.pozdnyakov.news.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.pozdnyakov.news.data.local.NewsDao
import ru.pozdnyakov.news.data.local.NewsDb
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object {

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