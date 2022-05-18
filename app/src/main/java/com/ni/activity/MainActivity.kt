package com.ni.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ni.teachersassistant.databinding.MainActivityLayoutBinding

class MainActivity  : AppCompatActivity() {
    private lateinit var binding: MainActivityLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}