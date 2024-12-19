package com.mmfsin.grisaceo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.mmfsin.grisaceo.R
import com.mmfsin.grisaceo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(500)
        setTheme(R.style.Theme_Grisaceo)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeStatusBarColor()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private fun changeStatusBarColor() {
        this.window?.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }
}