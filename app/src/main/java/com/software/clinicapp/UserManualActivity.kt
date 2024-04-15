package com.software.clinicapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.software.clinicapp.databinding.ActivityUserManualBinding

class UserManualActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserManualBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserManualBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}