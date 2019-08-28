package com.ahanuliak.iwatch.data.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Movie::class], version = 1)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MovieRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    "movie_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(MovieDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class MovieDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.movieDAO())
                    }
                }
            }
        }

        suspend fun populateDatabase(movieDAO: MovieDAO) {
            val movie = Movie("Hulk", "2003", "/gCQ4e8klADtzoa6bL7XLPnjiUIg.jpg")
            movieDAO.insert(movie)
        }
    }

}