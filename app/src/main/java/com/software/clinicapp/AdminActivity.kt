package com.software.clinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.software.clinicapp.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()

    }

    private fun setup(){
        initListeners()
    }

    private fun initListeners(){

        binding.cvManagementUsers.setOnClickListener { goToManagementUserActivity() }
        binding.cvhistorialCitas.setOnClickListener { goToHistorialCitas() }
        binding.cvReporteCitas.setOnClickListener { goToReports() }

        binding.btnLogOut.setOnClickListener {
            auth.signOut()
            showLoginScreen()
        }
    }



    private fun showLoginScreen(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun goToManagementUserActivity(){

    }

    private fun goToHistorialCitas(){

    }

    private fun goToReports(){

    }
}