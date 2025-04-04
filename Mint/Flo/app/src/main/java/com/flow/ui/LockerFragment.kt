package com.flow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flow.databinding.FragmentLockerBinding

class LockerFragment : Fragment() {

    private var _binding: FragmentLockerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLockerBinding.inflate(inflater, container, false)
        // 보관함에 필요한 UI 로직 작성
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
