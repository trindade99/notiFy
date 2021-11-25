package com.example.notify.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Query(value = "SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>


}