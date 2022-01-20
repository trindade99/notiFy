package com.example.notify.api_notify.dto
import com.example.notify.api_notify.model.User

data class UserDto (
    val status : String,
    val message : String,
    val user : List<User>,
    val token : String
)