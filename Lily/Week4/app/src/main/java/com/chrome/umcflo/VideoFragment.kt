package com.chrome.umcflo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chrome.umcflo.databinding.FragmentDetailBinding
import com.chrome.umcflo.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {

    lateinit var binding : FragmentVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }
}