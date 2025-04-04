package com.flow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flow.databinding.FragmentVideoBinding


class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        // 예시: binding.videoText.text = "앨범 관련 영상 목록"
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
