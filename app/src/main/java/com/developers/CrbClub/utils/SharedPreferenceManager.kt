package com.developers.CrbClub.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context)
{
    var context: Context? = null

    val myPrif:String = "CashStarz"

    val sharedPreferences : SharedPreferences = context.getSharedPreferences(myPrif,Context.MODE_PRIVATE)

    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveToken(Key:String,value: String)
    {
        editor.putString(Key,value)
        editor.apply()
        editor.commit()
    }

    fun getToken(Key: String):String?
    {
        return sharedPreferences.getString(Key,null)
    }

    fun addString(key: String?, str: String?) {
        editor.putString(key, str)
        editor.commit()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    fun clearSharedPreference() {
        editor.clear()
        editor.commit()
    }
}