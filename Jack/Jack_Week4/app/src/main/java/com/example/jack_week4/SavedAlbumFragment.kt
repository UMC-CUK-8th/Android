package com.example.jack_week4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jack_week4.databinding.FragmentSavedAlbumBinding

class SavedAlbumFragment : Fragment() {

    private lateinit var binding: FragmentSavedAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedAlbumBinding.inflate(inflater, container, false)
        binding.albumDummyTextView.text = "여기는 저장된 앨범 화면입니다 (더미 화면)"
        return binding.root
    }
}
