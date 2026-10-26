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

    @Query("DELETE FROM book WHERE id = :id")
    fun deleteBookById(id : Int)

    @Query("SELECT * FROM book")
    fun getBooks(): List<Book>


    @Query("Delete FROM sqlite_sequence where name = 'book'")
    fun resetIdCounter()
}