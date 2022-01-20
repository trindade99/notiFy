package com.example.notify.api_notify.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note (
    val id: Int,
    val user_name: String,
    val title: String,
    val description: String,
    val users_id : Int,
    val Body: String,
    val created_at : String
) : Parcelable

