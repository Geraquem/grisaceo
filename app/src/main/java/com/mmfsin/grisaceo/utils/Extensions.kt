package com.mmfsin.grisaceo.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.mmfsin.grisaceo.base.dialog.ErrorDialog

fun FragmentActivity.showErrorDialog() = this.showFragmentDialog(ErrorDialog())

fun FragmentActivity?.showFragmentDialog(dialog: DialogFragment) =
    this?.let { dialog.show(it.supportFragmentManager, "") }
