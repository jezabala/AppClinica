package com.software.clinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.software.clinicapp.databinding.ActivityMedicoBinding

class MedicoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicoBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }

    private fun setup() {
        initListener()
    }

    private fun initListener() {

        binding.cvPacientes.setOnClickListener {

        }

        binding.cvCitasPendientes.setOnClickListener {

        }

        binding.cvTratamientos.setOnClickListener {

        }

        binding.btnLogOut.setOnClickListener {
            auth.signOut()
            showLogin()
        }
    }

    private fun showLogin() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}