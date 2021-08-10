package com.soten.gominno.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.soten.gominno.R
import com.soten.gominno.databinding.FinishDialogBinding

class FinishFragment: DialogFragment(R.layout.finish_dialog) {

    private var _binding: FinishDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCancelable = false
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setWindowAnimations(R.style.MyAnimation_Window)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FinishDialogBinding.bind(view)

        Glide.with(this)
            .load(R.raw.gramde_test)
            .into(binding.imageView)

        binding.finishTextView.setOnClickListener {
            requireActivity().finish()
        }

        binding.cancelTextView.setOnClickListener {
            dismiss()
        }
    }

}