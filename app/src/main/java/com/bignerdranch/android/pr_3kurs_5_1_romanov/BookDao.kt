package com.bignerdranch.android.pr_3kurs_5_1_romanov

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Upsert
    fun updateOrInsertBook(book: Book)

    @Delete
    fun deleteBook(book: Book)

    @Query("SELECT * FROM book ORDER BY title ASC")
    fun getBooksOrderedByTitle(): List<Book>

    @Query("SELECT * FROM book ORDER BY series ASC")
    fun getBooksOrderedBySeries(): List<Book>

    @Query("SELECT * FROM book ORDER BY author ASC")
    fun getBooksOrderedByAuthor(): List<Book>

    @Query("Delete FROM sqlite_sequence where 'book'")
    fun resetIdCounter()
}