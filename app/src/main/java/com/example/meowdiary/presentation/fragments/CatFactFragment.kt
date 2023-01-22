package com.example.meowdiary.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.meowdiary.databinding.FragmentCatFactBinding
import com.example.meowdiary.model.api.CatFact
import com.example.meowdiary.viewmodel.CatFactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatFactFragment : Fragment() {

    private val catFactViewModel: CatFactViewModel by  activityViewModels()
    private var _binding: FragmentCatFactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatFactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGetNewFact.setOnClickListener {
            catFactViewModel.loadCatFact()
        }


    }

    override fun onStart() {
        super.onStart()
        val catFactObserver = Observer<CatFact> { newCatFact ->
            binding.tvCatFact.text = newCatFact.catFact
        }
        catFactViewModel.catFact.observe(requireActivity(), catFactObserver)

    }
}