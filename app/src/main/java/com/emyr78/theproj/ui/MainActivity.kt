package com.emyr78.theproj.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emyr78.theproj.R
import com.emyr78.theproj.databinding.ActivityMainBinding
import com.emyr78.theproj.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.frag_container,HomeFragment())
                .commit()
        }
        init()
    }

    private fun init(){

    }
}