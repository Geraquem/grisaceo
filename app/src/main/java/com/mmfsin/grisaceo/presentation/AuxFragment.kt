package com.mmfsin.grisaceo.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmfsin.grisaceo.base.BaseFragmentNoVM
import com.mmfsin.grisaceo.databinding.FragmentAuxBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuxFragment : BaseFragmentNoVM<FragmentAuxBinding>(){

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentAuxBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUI() {
        binding.apply {

        }
    }

    override fun setListeners() {
        binding.apply {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}