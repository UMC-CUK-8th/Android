package com.example.week2_jack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.week2_jack.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var mBinding : FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeBinding.inflate(inflater,container,false)

        mBinding = binding

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

}
