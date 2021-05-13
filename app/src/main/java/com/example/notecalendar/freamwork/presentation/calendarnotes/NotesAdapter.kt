package com.example.notecalendar.freamwork.presentation.calendarnotes

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notecalendar.R
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.model.SubNote
import com.example.notecalendar.freamwork.presentation.util.DF
import com.example.notecalendar.freamwork.presentation.util.DateUtils
import kotlinx.android.synthetic.main.fragment_calendar_notes.view.*
import kotlinx.android.synthetic.main.item_note.view.*
import kotlinx.android.synthetic.main.item_sub_note.view.*

class NotesAdapter(val noteListener: NoteListener? = null) : RecyclerView.Adapter<NotesAdapter.NoteHolder>() {
    inner class NoteHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false))

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = differ.currentList[position]
        holder.itemView.cl_root.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, getBackgroundColor(position)))
        holder.itemView.tv_note.text = note.title
        holder.itemView.tv_date.text = DateUtils.getDateWithFormat(note.date, DF.DATE_FORMAT, DF.HOUR_FORMAT)
        val expandedVisibility = if (note.isExpanded) View.VISIBLE else View.GONE
        holder.itemView.btn_delete.visibility = expandedVisibility
        holder.itemView.btn_edit.visibility = expandedVisibility
        holder.itemView.btn_delete.setOnClickListener { noteListener?.onDelete(note) }
        holder.itemView.btn_edit.setOnClickListener { noteListener?.onEdit(note) }
        holder.itemView.setOnClickListener {
            note.isExpanded = !note.isExpanded
            notifyItemChanged(position)
        }
        if (note.subNotes.isNotEmpty())
            attachSubNotes(holder, note.subNotes)
    }

    override fun getItemCount() = differ.currentList.size

   private fun attachSubNotes(holder: NoteHolder, subNotes : List<SubNote>) {
       holder.itemView.ll_sub_note.removeAllViews()
       subNotes.map {subNote->
           val view = LayoutInflater.from(holder.itemView.context).inflate(R.layout.item_sub_note,holder.itemView.ll_sub_note,false)
           val spannable = SpannableString(subNote.note)
           val bulletSpan = BulletSpan(holder.itemView.context.resources.getDimension(R.dimen.bulletPadding).toInt(),
               ContextCompat.getColor(holder.itemView.context,R.color.dark))
           spannable.setSpan(bulletSpan, 0,subNote.note.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
           view.tv_sub_note.text = spannable
           val hasCommand = subNote.comment.isNotEmpty()
           if (hasCommand){
               view.tv_sub_note_comment.text = holder.itemView.context.getString(R.string.brackets_format, subNote.comment)
               view.tv_sub_note_comment.visibility = View.VISIBLE
           }
           holder.itemView.ll_sub_note.addView(view)
       }
       holder.itemView.ll_sub_note.visibility = View.VISIBLE
    }

    fun updateItems(notes : List<Note>){
        differ.submitList(notes)
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