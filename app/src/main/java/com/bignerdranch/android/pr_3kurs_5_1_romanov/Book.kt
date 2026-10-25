package com.bignerdranch.android.pr_3kurs_5_1_romanov

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class Book( val title: String, val series: String, val author: String,@PrimaryKey(autoGenerate = true) val id: Int = 0) {
}