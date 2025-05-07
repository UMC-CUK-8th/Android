package com.example.jack_week4

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jack_week4.databinding.FragmentSavedSongBinding

class SavedSongFragment : Fragment() {

    private lateinit var binding: FragmentSavedSongBinding
    private lateinit var adapter: SongAdapter
    private val songList = mutableListOf<Song>(
        Song("Lilac", "아이유 (IU)", 0),
        Song("Blueming", "아이유 (IU)", 0),
        Song("Celebrity", "아이유 (IU)", 0),
        Song("스물셋", "아이유 (IU)", 0),
        Song("홀씨", "아이유 (IU)", 0),
        Song("Love Wins All", "아이유 (IU)", 0),
        Song("밤편지", "아이유 (IU)", 0),
        Song("팔레트", "아이유 (IU)", 0),
        Song("에잇", "아이유 (IU)", 0),
        Song("내 손을 잡아", "아이유 (IU)", 0),
        Song("어푸", "아이유 (IU)", 0),
        Song("Shopper", "아이유 (IU)", 0),
        Song("좋은 날", "아이유 (IU)", 0),
        Song("Strawberry Moon", "아이유 (IU)", 0),
        Song("금요일에 만나요", "아이유 (IU)", 0)


    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)

        // 어댑터 설정
        adapter = SongAdapter(songList as ArrayList<Song>)

        // 아이템 클릭 시 삭제 기능 구현
        adapter.setMyItemClickListener(object : SongAdapter.MyItemClickListener {
            override fun onRemoveSong(position: Int) {
                if (position >= 0 && position < songList.size) {
                    songList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    adapter.notifyItemRangeChanged(position, songList.size)
                }
            }
        })

        binding.savedSongRecyclerView.adapter = adapter
        binding.savedSongRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
}
