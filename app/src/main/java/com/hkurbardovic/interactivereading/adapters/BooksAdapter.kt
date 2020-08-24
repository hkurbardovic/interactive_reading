package com.hkurbardovic.interactivereading.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hkurbardovic.interactivereading.databinding.ItemBookBinding
import com.hkurbardovic.interactivereading.models.Book

class BooksAdapter : ListAdapter<Book, BooksAdapter.ViewHolder>(BooksDiffCallback()) {

    private lateinit var onBookClickListener: OnBookClickListener

    interface OnBookClickListener {
        fun onBookClick(book: Book)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = getItem(position)
        holder.apply {
            bind(createOnClickListener(book), book)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createOnClickListener(book: Book): View.OnClickListener {
        return View.OnClickListener {
            onBookClickListener.onBookClick(book)
        }
    }

    fun setOnBookClickListener(onBookClickListener: OnBookClickListener) {
        this.onBookClickListener = onBookClickListener
    }

    class ViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: View.OnClickListener, book: Book) {
            binding.apply {
                this.clickListener = clickListener
                this.book = book
                executePendingBindings()
            }
        }
    }
}

private class BooksDiffCallback : DiffUtil.ItemCallback<Book>() {

    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}