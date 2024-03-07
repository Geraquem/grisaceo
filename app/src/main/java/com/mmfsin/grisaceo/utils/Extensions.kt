package com.mmfsin.grisaceo.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.mmfsin.grisaceo.base.dialog.ErrorDialog
import com.mmfsin.grisaceo.presentation.error.NetworkErrorDialog

fun FragmentActivity.showErrorDialog() = this.showFragmentDialog(ErrorDialog())

fun FragmentActivity.showNetworkErrorDialog(retry: () -> Unit) =
    this.showFragmentDialog(NetworkErrorDialog(retry))

fun FragmentActivity?.showFragmentDialog(dialog: DialogFragment) =
    this?.let { dialog.show(it.supportFragmentManager, "") }