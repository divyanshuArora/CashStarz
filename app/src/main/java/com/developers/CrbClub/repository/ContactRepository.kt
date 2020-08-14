package com.developers.CrbClub.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developers.a24mpower.activities.activity.services.retrofit.retrofit.ApiInterface
import com.developers.CrbClub.requests.ContactRequest
import com.developers.CrbClub.responses.ContactResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactRepository
{

    companion object RegisterFactory
    {

        fun getInstance():ContactRepository
        {
            return ContactRepository()
        }
    }


    fun contactApiCall(contactRequest: ContactRequest): LiveData<ContactResponse>
    {
        val data = MutableLiveData<ContactResponse>()

        ApiInterface.create().contactAPi(contactRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({data.value = it},{
                Log.d("ContactRepo","${it.message}")
            })
        return  data
    }





}