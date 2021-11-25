package com.example.notify.repository;

import androidx.lifecycle.LiveData;
import com.example.notify.data.NoteDao
import com.example.notify.model.Note

class NoteRepository(var noteDao: NoteDao) {

    val readAllData: LiveData<kotlin.collections.List<Note>> = noteDao.readAllData()

    suspend fun addUser(note: Note){
        noteDao.addUser(note)
    }

    suspend fun updateUser(note: Note){
        noteDao.updateUser(note)
    }
}
