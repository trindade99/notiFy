package com.example.notify.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notify.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(note: Note)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateUser(note: Note)

    @Query(value = "SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Note>>


}