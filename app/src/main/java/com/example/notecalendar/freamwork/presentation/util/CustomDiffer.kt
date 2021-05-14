package com.example.notecalendar.freamwork.presentation.util

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CustomDiffer<T,K : RecyclerView.ViewHolder>(adapter : RecyclerView.Adapter<K>, callback: DiffUtil.ItemCallback<T>) : AsyncListDiffer<T>(adapter,callback) {
    fun addItem(item : T){
        val list = this.currentList.map { it } as ArrayList
        list.add(item)
        submitList(list)
    }

    fun removeItem(item: T){
        val list = this.currentList.map { it } as ArrayList
        list.remove(item)
        submitList(list)
    }
}