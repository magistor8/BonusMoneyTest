package com.magistor8.bonusmoneytest.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.magistor8.bonusmoneytest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}