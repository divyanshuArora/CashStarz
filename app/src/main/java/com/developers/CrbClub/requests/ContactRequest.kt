package com.developers.CrbClub.requests
import com.google.gson.annotations.SerializedName



data class ContactRequest(
    @SerializedName("user_contacts")
    val userContacts: String,
    @SerializedName("user_imei")
    val userImei: String

)