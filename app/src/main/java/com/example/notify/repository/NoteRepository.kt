package com.example.notify.repository;

import androidx.lifecycle.LiveData;
import com.example.notify.data.dao.NoteDao
import com.example.notify.data.entities.Note

class NoteRepository(var noteDao: NoteDao) {

    val readAllData: LiveData<kotlin.collections.List<Note>> = noteDao.readAllNotes()

    suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }
}
