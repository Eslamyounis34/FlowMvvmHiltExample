package com.example.flowmvvmexample.common.utils

import android.app.Activity
import cn.pedant.SweetAlert.SweetAlertDialog

fun Activity.loadingAlert(title: String) {

    var sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
    sweetAlertDialog.setTitleText(title)
    sweetAlertDialog.setCancelable(false)

    sweetAlertDialog.show()
}