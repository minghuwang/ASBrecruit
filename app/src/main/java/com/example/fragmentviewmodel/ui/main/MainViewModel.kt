package com.example.fragmentviewmodel.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fragmentviewmodel.model.Model
import com.example.fragmentviewmodel.model.Note
import com.example.fragmentviewmodel.utils.Utils
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    private val liveDataNotes = MutableLiveData<List<Note>>()
    private val liveDataPresure = MutableLiveData<String>()
    fun InitData() {
        println("getData")
        if (Utils.Pressure) {
            liveDataPresure.value = "0"
        } else {
            viewModelScope.launch {
                var postModel = Model()
                liveDataNotes.value = postModel.HttpGetData()

            }
        }
    }

    fun setPresure(s: String) {
        liveDataPresure.value = s
    }
    fun getPresure(): LiveData<String>{
        return liveDataPresure
    }

    fun getNotes(): LiveData<List<Note>> {
        return liveDataNotes
    }

}