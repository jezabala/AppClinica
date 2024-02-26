package com.software.clinicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.software.clinicapp.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()

    }

    private fun setup(){
        initListeners()
    }

    private fun initListeners(){

        binding.cvProgramarCitas.setOnClickListener {

        }
        binding.cvHistorialCitasProgramadas.setOnClickListener {

        }

        binding.cvTratamientos.setOnClickListener {

        }

        binding.btnLogOut.setOnClickListener {
            auth.signOut()
        }
    }
}