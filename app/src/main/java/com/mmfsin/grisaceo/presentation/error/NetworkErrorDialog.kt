package com.mmfsin.grisaceo.presentation.error

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.grisaceo.databinding.DialogErrorBinding
import com.mmfsin.grisaceo.base.BaseDialog
import com.mmfsin.grisaceo.databinding.DialogNetworkErrorBinding

class NetworkErrorDialog(val retry: () -> Unit) : BaseDialog<DialogNetworkErrorBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogNetworkErrorBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                retry()
                dismiss()
            }

            btnClose.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
                dismiss()
            }
        }
    }
}