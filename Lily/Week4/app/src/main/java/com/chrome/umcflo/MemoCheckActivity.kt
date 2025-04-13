package com.chrome.umcflo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chrome.umcflo.databinding.ActivityMemoBinding
import com.chrome.umcflo.databinding.ActivityMemoCheckBinding

class MemoCheckActivity : AppCompatActivity() {

    lateinit var binding : ActivityMemoCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemoCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("memo")) {
            binding.memoCheckText.text = intent.getStringExtra("memo")!!
        }
    }
}