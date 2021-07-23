package com.example.fragmentviewmodel.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fragmentviewmodel.model.Model
import com.example.fragmentviewmodel.model.Note
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.ArrayList



class MainViewModel : ViewModel() {
    private val liveDataNotes = MutableLiveData<List<Note>>()

    fun InitData() {
        println("getData")
        viewModelScope.launch {
            var postModel = Model()
            liveDataNotes.value = postModel.HttpGetData()
        }
    }

    fun getNotes(): LiveData<List<Note>> {
        return liveDataNotes
    }

}