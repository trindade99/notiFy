package com.example.notify.api_notify.dto

import com.example.notify.api_notify.model.Note

data class NoteDto(
    val status : String,
    val title : String,
    val description : String,
    val note : Note
)

