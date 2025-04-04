package com.flow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flow.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {

    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)

        // 앨범 제목, 가수명 설정 (오버레이 텍스트)
        val albumTitle = arguments?.getString("albumTitle") ?: "앨범 제목"
        val albumSinger = arguments?.getString("albumSinger") ?: "앨범 가수"

        binding.albumTitleTv.text = albumTitle
        binding.albumSingerTv.text = albumSinger

        // 취향MIX 스위치 (버튼 대신 스위치 사용)
        binding.albumMixSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // 취향 MIX ON (필요한 경우 아이콘 업데이트)
                // binding.albumMixImage.setImageResource(R.drawable.ic_mymix_active)
            } else {
                // 취향 MIX OFF
                // binding.albumMixImage.setImageResource(R.drawable.ic_mymix_inactive)
            }
        }

        // 앨범 가사 샘플 설정
        binding.albumLyricsTv.text = "여기에 앨범 가사가 표시됩니다.\n(샘플 가사 내용)"

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
