package ru.pozdnyakov.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleDbModel::class, SubscriptionDbModel::class], version = 1)
abstract class NewsDb: RoomDatabase() {

    abstract fun newsDao(): NewsDao

}