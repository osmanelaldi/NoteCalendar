package com.example.notecalendar.freamwork.presentation.noteinput

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notecalendar.R
import com.example.notecalendar.business.domain.model.SubNote
import com.example.notecalendar.business.domain.model.SubNoteBuilderItem
import com.example.notecalendar.business.domain.model.SubNotesWrapper
import com.example.notecalendar.databinding.ItemAddSubNoteBinding
import com.example.notecalendar.freamwork.presentation.util.CustomDiffer

class SubNoteAdapter : RecyclerView.Adapter<SubNoteAdapter.SubNoteHolder>() {

    inner class SubNoteHolder(val binding: ItemAddSubNoteBinding) : RecyclerView.ViewHolder(binding.root)

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
        return SubNoteHolder(ItemAddSubNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SubNoteHolder, position: Int) {
        val subNoteBuilderItem = differ.currentList[position]
        with(holder){
            binding.tilTitle.error = if (subNoteBuilderItem.error) holder.itemView.context.getString(R.string.note_should_not_be_empty) else null
            binding.ivRemove.setOnClickListener {
                removeSubNote(subNoteBuilderItem)
            }
            binding.etTitle.addTextChangedListener {
                subNoteBuilderItem.note = it.toString()
            }
            binding.etDescription.addTextChangedListener {
                subNoteBuilderItem.comment = it.toString()
            }
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
            else
                subNotes.add(SubNote(subNoteBuilderItem.note,subNoteBuilderItem.comment))
        }
            return SubNotesWrapper(subNotes, errors = false)
    }

    fun showError(){
        notifyDataSetChanged()
    }

}