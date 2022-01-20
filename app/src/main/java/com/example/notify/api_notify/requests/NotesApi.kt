package com.example.notify.api_notify.requests

import com.example.notify.api_notify.dto.NoteDto
import com.example.notify.api_notify.model.Note
import retrofit2.Call
import retrofit2.http.*

interface NotesApi {
    @GET("notes/read")
    fun getReports(@Header("Authorization") token: String): Call<List<Note>>

    @FormUrlEncoded
    @POST("notes/create")
    fun createReport(
        @Header("Authorization") token: String,
        @Field("users_id") users_id: String?,
        @Field("title") title: String,
        @Field("description") description: String
    ): Call<NoteDto>

    @FormUrlEncoded
    @POST("notes/update")
    fun updateReport(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("description") description: String
    ): Call<NoteDto>

    @FormUrlEncoded
    @POST("notes/delete")
    fun deleteReport(@Header("Authorization") token: String, @Field("id") id: Int): Call<NoteDto>
}
