package com.example.meowdiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowdiary.model.api.CatFact
import com.example.meowdiary.model.CatFactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CatFactViewModel @Inject constructor(
    private val catFactRepository: CatFactRepository
) : ViewModel() {

    private val _catFact = MutableLiveData<CatFact>()
    val catFact: LiveData<CatFact> = _catFact

    fun loadCatFact() {
        viewModelScope.launch {
            try {
                _catFact.value = catFactRepository.getCatFact()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}