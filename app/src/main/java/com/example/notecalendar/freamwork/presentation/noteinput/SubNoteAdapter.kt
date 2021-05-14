package com.example.notecalendar.freamwork.presentation.noteinput

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notecalendar.R
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.model.SubNote
import kotlinx.android.synthetic.main.item_add_sub_note.view.*

class SubNoteAdapter : RecyclerView.Adapter<SubNoteAdapter.SubNoteHolder>() {

    inner class SubNoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val callback = object : DiffUtil.ItemCallback<SubNote>() {

        override fun areItemsTheSame(oldItem: SubNote, newItem: SubNote): Boolean {
            return oldItem.note == newItem.note
        }

        override fun areContentsTheSame(oldItem: SubNote, newItem: SubNote): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubNoteHolder {
        return SubNoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_add_sub_note,parent,false)   )
    }

    override fun onBindViewHolder(holder: SubNoteHolder, position: Int) {
        holder.itemView.et_description.hint = holder.itemView.context.getString(R.string.sub_note_description, position.toString())
    }

    override fun getItemCount() = differ.currentList.size

    fun addSubNote(){
        val hint =
    }

}