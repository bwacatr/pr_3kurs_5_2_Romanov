package com.bignerdranch.android.pr_3kurs_5_1_romanov

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bignerdranch.android.pr_3kurs_5_1_romanov.Dependencies.bookRepository

@Database(
    entities = [Book::class],
    version = 1
)
abstract class BookDatabase: RoomDatabase() {
    abstract fun getBookDao() : BookDao

}


object Dependencies {


    private lateinit var applicationContext: Context

    fun resetBookAutoIncrement() {

        bookDatabase.openHelper.writableDatabase
            .execSQL("DELETE FROM sqlite_sequence WHERE name = 'book'")
    }

    fun init(context: Context) {
        applicationContext = context
    }

    private val bookDatabase: BookDatabase by lazy {
        Room.databaseBuilder(applicationContext, BookDatabase::class.java, "database.db")
            .build()
    }

    val bookRepository: BookRepository by lazy {
        BookRepository(bookDatabase.getBookDao())
    }
}