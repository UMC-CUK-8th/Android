package com.flow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.flo.ui.SavedSongFragment
import com.flow.databinding.FragmentLockerBinding
import com.flow.ui.adapters.LockerVPAdapter
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    private var _binding: FragmentLockerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLockerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = adapter

        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp) { tab, pos ->
            tab.text = if (pos == 0) "저장한 곡" else "음악파일"
        }.attach()

        // LockerFragment.kt 수정

        binding.lockerContentVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    val fragment = childFragmentManager.findFragmentByTag("f0") as? SavedSongFragment
                    fragment?.setSelectAllButton(binding.lockerSelectAllImgIv)
                    fragment?.setDeleteButton(binding.lockerDeleteIv)
                    binding.lockerSelectAllImgIv.setOnClickListener {
                        fragment?.enterDeleteMode()
                    }
                } else {
                    binding.lockerDeleteIv.visibility = View.GONE
                }
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
