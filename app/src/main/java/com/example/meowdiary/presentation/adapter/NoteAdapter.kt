package com.example.meowdiary.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.meowdiary.databinding.NoteLayoutAdapterBinding
import com.example.meowdiary.model.db.Note.Note
import com.example.meowdiary.presentation.fragments.HomeFragmentDirections
import java.text.SimpleDateFormat
import java.util.*


class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}

class NoteViewHolder(binding: NoteLayoutAdapterBinding): RecyclerView.ViewHolder
    (binding.root) {
    private val dateFormat = SimpleDateFormat("dd MMMM ")
    private val noteTitle = binding.tvNoteTitle
    private val noteBody = binding.tvNoteBody
    private val noteDate = binding.noteDate

    fun bind(note: Note) {
        var date: Date
        noteTitle.text = note.noteTitle
        noteBody.text = note.noteBody
        if (note.updated_at == null) {
            date = note.created_at
        } else {
            date = note.updated_at
        }
        noteDate.text = dateFormat.format(date)

    }

    }

class NoteAdapter : RecyclerView.Adapter<NoteViewHolder>() {

    val differ = AsyncListDiffer(this, NoteDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]
        holder.bind(currentNote)

        holder.itemView.setOnClickListener { view ->
           val direction2 = HomeFragmentDirections.actionHomeFragmentToNoteFragment(true, currentNote)
            view.findNavController().navigate(direction2)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}

