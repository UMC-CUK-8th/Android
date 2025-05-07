package com.example.jack_week4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jack_week4.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator

class LibraryFragment : Fragment() {
    lateinit var binding: FragmentLibraryBinding
    private val information = arrayListOf("저장한곡", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)

        val libraryAdapter = LibraryVPAdapter(this)
        binding.libraryContentVp.adapter = libraryAdapter
        TabLayoutMediator(binding.libraryContentTb, binding.libraryContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

}
