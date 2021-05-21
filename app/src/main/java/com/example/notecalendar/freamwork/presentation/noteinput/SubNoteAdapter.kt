package com.example.notecalendar.freamwork.presentation.noteinput

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notecalendar.R
import com.example.notecalendar.business.domain.model.SubNote
import com.example.notecalendar.business.domain.model.SubNoteBuilderItem
import com.example.notecalendar.business.domain.model.SubNotesWrapper
import com.example.notecalendar.freamwork.presentation.util.CustomDiffer
import kotlinx.android.synthetic.main.item_add_sub_note.view.*

class SubNoteAdapter : RecyclerView.Adapter<SubNoteAdapter.SubNoteHolder>() {

    inner class SubNoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val callback = object : DiffUtil.ItemCallback<SubNoteBuilderItem>() {

        override fun areItemsTheSame(oldItem: SubNoteBuilderItem, newItem: SubNoteBuilderItem): Boolean {
            return oldItem.note == newItem.note
        }

        override fun areContentsTheSame(oldItem: SubNoteBuilderItem, newItem: SubNoteBuilderItem): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = CustomDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubNoteHolder {
        return SubNoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_add_sub_note,parent,false)   )
    }

    override fun onBindViewHolder(holder: SubNoteHolder, position: Int) {
        val subNoteBuilderItem = differ.currentList[position]
        holder.itemView.til_title.error = if (subNoteBuilderItem.error) holder.itemView.context.getString(R.string.note_should_not_be_empty) else null
        holder.itemView.iv_remove.setOnClickListener {
            removeSubNote(subNoteBuilderItem)
        }
        holder.itemView.et_title.addTextChangedListener {
            subNoteBuilderItem.note = it.toString()
        }
        holder.itemView.et_description.addTextChangedListener {
            subNoteBuilderItem.comment = it.toString()
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun addSubNote(){
        differ.addItem(SubNoteBuilderItem())
    }

    private fun removeSubNote(subNoteBuilderItem : SubNoteBuilderItem){
        differ.removeItem(subNoteBuilderItem)
    }

    fun retrieveSubNotes() : SubNotesWrapper{
        val subNotes = arrayListOf<SubNote>()
        val errors = arrayListOf<String>()
        differ.currentList.forEachIndexed { index, subNoteBuilderItem ->
            val error = subNoteBuilderItem.note.isEmpty()
            subNoteBuilderItem.error = error
            if (error)
                return SubNotesWrapper(listOf(), true)
        }
            return SubNotesWrapper(subNotes, errors = false)
    }

    fun showError(){
        notifyDataSetChanged()
    }

}