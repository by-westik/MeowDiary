package com.example.meowdiary.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.meowdiary.R
import com.example.meowdiary.databinding.FragmentHomeBinding
import com.example.meowdiary.model.db.Note.Note
import com.example.meowdiary.presentation.adapter.NoteAdapter
import com.example.meowdiary.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val notesViewModel: NoteViewModel by activityViewModels()
    private lateinit var noteAdapter: NoteAdapter

    private fun searchListener() {
        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val query = binding.etSearch.text.toString()
                notesViewModel.searchNote(query)?.observe(
                    requireActivity()
                ) {
                    setAdapter(it)
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        showNotes()
        return binding.root
    }

    private fun onDataChangeObserver() {
        notesViewModel.getAllNote().observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it.isEmpty()) {
                    binding.imvNotesNotFound.visibility = View.VISIBLE
                    binding.tvNoteNotFound.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.imvNotesNotFound.visibility = View.GONE
                    binding.tvNoteNotFound.visibility = View.GONE
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onDataChangeObserver()
        binding.fabAddNote.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNoteFragment(false, null)
            it.findNavController().navigate(action)
        }
        searchListener()
    }

    private fun showNotes() {
        noteAdapter = NoteAdapter()
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            adapter = noteAdapter
        }
    }

    private fun setAdapter(notes: List<Note>) {
        if(notes.isNotEmpty()) {
            noteAdapter.differ.submitList(notes)
        } else {
            noteAdapter.differ.submitList(ArrayList<Note>())
        }
    }

    override fun onStart() {
        super.onStart()
        notesViewModel.getAllNote().observe(requireActivity(), Observer {
            setAdapter(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}