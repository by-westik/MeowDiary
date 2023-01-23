package com.example.meowdiary.presentation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.meowdiary.R
import com.example.meowdiary.databinding.FragmentNoteBinding
import com.example.meowdiary.model.db.Note.Note
import com.example.meowdiary.presentation.utils.toast
import com.example.meowdiary.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel: NoteViewModel by activityViewModels()
    private val args: NoteFragmentArgs by navArgs()
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy hh:mm a")
    private lateinit var noteDate: Date
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNoteBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnDone.setOnClickListener {
            saveNote(mView)
        }
        if(!args.isUpdate) {
            binding.btnDelete.visibility = View.GONE
        } else {
            binding.btnDelete.visibility = View.VISIBLE
        }
        binding.btnDelete.setOnClickListener {
            deleteNote(mView)
        }
    }


    override fun onResume() {
        super.onResume()
        setDefaultData()
    }

    private fun setDefaultData() {
        noteDate = Date()
        if (args.isUpdate) {
            val currentNote = args.note
            val date: Date
            if (currentNote?.updated_at != null) {
                date = currentNote.updated_at
            } else {
                date = currentNote!!.created_at
            }
            binding.currentDate.text = dateFormat.format(date)
            binding.etNoteBody.setText(currentNote.noteBody)
            binding.etNoteTitle.setText(currentNote.noteTitle)
        } else {
            binding.currentDate.text = dateFormat.format(noteDate)
        }

    }

    private fun saveNote(view: View) {
        val noteTitle = binding.etNoteTitle.text.toString().trim()
        val noteBody = binding.etNoteBody.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            if(args.isUpdate) {
                val currentNote = args.note
                val note =
                    currentNote?.let {
                        Note(currentNote.id, noteTitle, noteBody, it.created_at, noteDate)
                    }
                note?.let { noteViewModel.updateNote(it) }
            } else {
                val note = Note(0, noteTitle, noteBody, noteDate, null)

                noteViewModel.addNote(note)
                activity?.toast("Запись сохранена")
            }
            view.findNavController().navigate(R.id.action_noteFragment_to_homeFragment)
        } else {
            activity?.toast("Пожалуйста, введите название")
        }
    }

    private fun deleteNote(view: View) {
        args.note?.let {
            noteViewModel.deleteNote(it)
            activity?.toast("Запись удалена")
        }

        view.findNavController().navigate(R.id.action_noteFragment_to_homeFragment)
    }



}