package com.example.asbolsyn.utils.views

import android.os.Bundle
import com.example.asbolsyn.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class RoundedBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?) = BottomSheetDialog(requireContext(), theme)
}