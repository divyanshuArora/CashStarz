package com.developers.a24mpower.activities.activity.services.retrofit.retrofit

import com.developers.CrbClub.requests.ContactRequest
import com.developers.CrbClub.responses.ContactResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.google.gson.GsonBuilder

interface ApiInterface {


    @POST("contact_list.php")
    fun contactAPi(@Body contactRequest: ContactRequest): Observable<ContactResponse>


    companion object Factory {
        fun create(): ApiInterface {

//                    val logInter = HttpLoggingInterceptor()
//                    logInter.setLevel(HttpLoggingInterceptor.Level.BODY)
//                    val mIntercepter: OkHttpClient = OkHttpClient.Builder()
//                        .addInterceptor(logInter)
//                        .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://www.cashstarz.com/trade/")
                //.client(mIntercepter)
                .build()

            return retrofit.create(ApiInterface::class.java)

        }
    }

}


