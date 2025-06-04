package com.example.jack_week4.uis.main.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jack_week4.R
import com.example.jack_week4.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.NoDimBottomSheetDialog
    lateinit var binding : FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomSheetIv1.setOnClickListener {
        }

        binding.bottomSheetIv2.setOnClickListener {
        }

        binding.bottomSheetIv3.setOnClickListener {
        }

        binding.bottomSheetIv4.setOnClickListener {
        }
    }
}