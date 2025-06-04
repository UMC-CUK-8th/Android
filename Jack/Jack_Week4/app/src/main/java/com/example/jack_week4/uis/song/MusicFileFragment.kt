package com.example.jack_week4.uis.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jack_week4.databinding.FragmentLibraryMusicfileBinding

class MusicFileFragment: Fragment() {

    lateinit var binding: FragmentLibraryMusicfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLibraryMusicfileBinding.inflate(inflater, container, false)

        return binding.root
    }
}