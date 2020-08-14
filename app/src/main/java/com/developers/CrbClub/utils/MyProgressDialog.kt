package com.developers.a24mpower.activities.activity.utils

import android.app.AlertDialog
import android.content.Context
import com.developers.CrbClub.R

class MyProgressDialog(context: Context):AlertDialog(context)
{
    init {
        window!!.setBackgroundDrawableResource(R.color.white_transparent)

    }

    override fun show() {
        super.show()
        setContentView(R.layout.dialog_progress)
    }

}