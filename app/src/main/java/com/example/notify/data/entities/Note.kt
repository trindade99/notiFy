package com.example.notify.data.entities

import android.os.Parcelable
import  androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName="notes")
class Note (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val note: String
): Parcelable
