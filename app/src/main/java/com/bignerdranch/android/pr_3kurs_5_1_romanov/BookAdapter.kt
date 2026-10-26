package com.bignerdranch.android.pr_3kurs_5_1_romanov

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import org.w3c.dom.Text


class BookAdapter(
    val books : MutableList<Book>
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>()
{
    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_data,parent,false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: BookViewHolder,
        position: Int
    )
    {



        holder.itemView.findViewById<TextView>(R.id.id).apply {
            text = books[position].id.toString()
        }

        holder.itemView.findViewById<TextView>(R.id.title).apply {
            text = books[position].title
        }

        holder.itemView.findViewById<TextView>(R.id.series).apply {
            text = books[position].series
        }

        holder.itemView.findViewById<TextView>(R.id.author).apply {
            text = books[position].author
        }
    }



    override fun getItemCount(): Int {
        return books.size
    }

    fun useList(newBooks: List<Book>)
    {
        books.clear()
        newBooks.forEach { books.add(it) }
        notifyDataSetChanged()
    }
}