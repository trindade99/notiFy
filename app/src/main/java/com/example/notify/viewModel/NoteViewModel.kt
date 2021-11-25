package com.example.notify.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.notify.data.NoteDatabase
import com.example.notify.repository.NoteRepository
import com.example.notify.model.Note
import kotlinx.coroutines.launch


class NoteViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val userDao = NoteDatabase.getDatabase(application).userDao()
        repository = NoteRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(note: Note){

        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(note)
        }
    }

    fun updateUser(note: Note){

        viewModelScope.launch(Dispatchers.IO){
            repository.updateUser(note)
        }
    }

    fun deleteUser(note: Note){

        viewModelScope.launch(Dispatchers.IO){
            repository.deleteUser(note)
        }
    }

}