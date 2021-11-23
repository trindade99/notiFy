package com.example.notify.data;

import androidx.lifecycle.LiveData;

class UserRepository(var userDao: UserDao) {

    val readAllData: LiveData<kotlin.collections.List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
}
