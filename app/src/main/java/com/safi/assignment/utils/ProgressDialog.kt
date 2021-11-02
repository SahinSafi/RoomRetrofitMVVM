package com.safi.assignment.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.safi.assignment.R

class ProgressDialog(context: Context?) : Dialog(context!!) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)

        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onBackPressed() {
        return
    }

}