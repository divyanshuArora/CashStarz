package com.developers.CrbClub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.developers.CrbClub.repository.ContactRepository
import com.developers.CrbClub.requests.ContactRequest
import com.developers.CrbClub.responses.ContactResponse

class ContactViewModel(application: Application): AndroidViewModel(application)
{

    fun contactSend(contactRequest: ContactRequest):LiveData<ContactResponse>
    {
        return  ContactRepository.getInstance().contactApiCall(contactRequest)
    }


}