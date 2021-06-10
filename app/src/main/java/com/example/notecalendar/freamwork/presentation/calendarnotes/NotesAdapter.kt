package com.example.notecalendar.freamwork.presentation.calendarnotes

import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notecalendar.R
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.model.SubNote
import com.example.notecalendar.databinding.ItemNoteBinding
import com.example.notecalendar.databinding.ItemSubNoteBinding
import com.example.notecalendar.freamwork.presentation.util.DF
import com.example.notecalendar.freamwork.presentation.util.DateUtils

class NotesAdapter(val noteListener: NoteListener? = null) : RecyclerView.Adapter<NotesAdapter.NoteHolder>() {
    inner class NoteHolder(val binding : ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    private val callback = object : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = differ.currentList[position]
        with(holder){
            binding.clRoot.setBackgroundColor(ContextCompat.getColor(holder.binding.root.context, getBackgroundColor(position)))
            binding.tvNote.text = note.title
            binding.tvDate.text = DateUtils.getDateWithFormat(note.date, DF.DATE_FORMAT, DF.HOUR_FORMAT)
            note.description?.let {
                binding.tvNoteDescription.visibility = View.VISIBLE
                binding.tvNoteDescription.text = note.description
            }
            val expandedVisibility = if (note.isExpanded) View.VISIBLE else View.GONE
            binding.btnDelete.visibility = expandedVisibility
            binding.btnEdit.visibility = expandedVisibility
            binding.btnDelete.setOnClickListener { noteListener?.onDelete(note) }
            binding.btnEdit.setOnClickListener { noteListener?.onEdit(note) }
            binding.root.setOnClickListener {
                note.isExpanded = !note.isExpanded
                notifyItemChanged(position)
            }
            binding.btnEdit.setOnClickListener {
                noteListener?.onEdit(note)
            }
            binding.btnDelete.setOnClickListener {
                noteListener?.onDelete(note)
            }
            if (note.subNotes.isNotEmpty())
                attachSubNotes(holder, note.subNotes)
        }
    }

    override fun getItemCount() = differ.currentList.size

   private fun attachSubNotes(holder: NoteHolder, subNotes : List<SubNote>) {
       with(holder){
           binding.llSubNote.removeAllViews()
           subNotes.map {subNote->
               val subNoteBinding = ItemSubNoteBinding.inflate(LayoutInflater.from(holder.binding.root.context))
               val spannable = SpannableString(subNote.note)
               val bulletSpan = BulletSpan(holder.itemView.context.resources.getDimension(R.dimen.bulletPadding).toInt(),
                   ContextCompat.getColor(holder.itemView.context,R.color.dark))
               spannable.setSpan(bulletSpan, 0,subNote.note.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
               subNoteBinding.tvSubNote.text = spannable
               subNote.comment?.let { comment->
                   subNoteBinding.tvSubNoteComment.text = holder.itemView.context.getString(R.string.brackets_format, comment)
                   subNoteBinding.tvSubNoteComment.visibility = View.VISIBLE
               }
               holder.binding.llSubNote.addView(subNoteBinding.root)
           }
           binding.llSubNote.visibility = View.VISIBLE
       }
    }

    fun clear(){
        differ.submitList(listOf())
    }

    fun updateItems(notes : List<Note>){
        differ.submitList(notes)
    }

    fun deleteItem(note: Note){
        val tem = differ.currentList.map { it } as ArrayList
        tem.removeIf { it.id == note.id }
        differ.submitList(tem)
    }

    private fun getBackgroundColor(index : Int) : Int{
        return when{
            index % 4 == 0 -> R.color.orange
            index % 3 == 0 -> R.color.yellow
            index % 2 == 0 -> R.color.purple
            else -> R.color.orange_red
        }
    }
}

interface NoteListener{
    fun onDelete(note : Note)
    fun onEdit(note : Note)
}