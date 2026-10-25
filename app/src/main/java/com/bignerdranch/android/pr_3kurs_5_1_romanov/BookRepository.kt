package com.bignerdranch.android.pr_3kurs_5_1_romanov

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BookRepository(private val bookDao: BookDao) {
    suspend fun updateOrInsertBook(book: Book) {
        withContext(Dispatchers.IO) {
            bookDao.updateOrInsertBook(book)
        }
    }

    suspend fun getBooksOrderedByTitle() : List<Book> {
        return withContext(Dispatchers.IO) {
            return@withContext bookDao.getBooksOrderedByTitle()
        }
    }

    suspend fun deleteBook(book: Book) {
        withContext(Dispatchers.IO) {
            bookDao.deleteBook(book)
        }
    }

    suspend fun resetIdCounter() {
        withContext(Dispatchers.IO) {
            bookDao.resetIdCounter()
        }
    }
}