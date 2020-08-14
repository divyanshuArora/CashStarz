package com.developers.CrbClub.responses


import com.google.gson.annotations.SerializedName

data class ContactResponse(
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String
)