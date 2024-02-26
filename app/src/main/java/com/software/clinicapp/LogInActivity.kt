package com.software.clinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.software.clinicapp.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    companion object {
        const val AUTH_COLLECTION = "auth"
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val homeIntent = Intent(this@LogInActivity, MainActivity::class.java)
            startActivity(homeIntent)
            finish()
        }

    }

    private lateinit var binding: ActivityLogInBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, callback)

        setup()
    }

    private fun setup() {
        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text!!.isNotEmpty() && binding.etPassword.text!!.isNotEmpty()) {
                auth.signInWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {

                        showHome(binding.etEmail.text.toString())

                        // Procedimiento para verificar email
                        //val user = auth.currentUser
                        //val emailVerified = user?.isEmailVerified
                        /*if (emailVerified == true) {
                            // Aqui va el showHome() en caso de verificacion de email
                            binding.etEmail.setText("")
                            binding.etPassword.setText("")
                        } else {
                            Toast.makeText(
                                this,
                                "Debe verificar el correo para iniciar sesion",
                                Toast.LENGTH_LONG
                            ).show()
                        } */

                    } else {
                        Toast.makeText(this, "Error al iniciar sesion", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        //binding.tvForgotPassword.setOnClickListener { showForgotPassword() }
    }

    private fun showHome(email: String) {
        if (email.isNotEmpty()) {
            db.collection(AUTH_COLLECTION).whereEqualTo("email", email)
                .get().addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val documentSnapshot = querySnapshot.documents[0]
                        when (documentSnapshot.getString("rol")) {
                            "ADMIN" -> {
                                val adminActivity = Intent(this, AdminActivity::class.java)
                                startActivity(adminActivity)
                                finish()
                            }

                            "MEDICO" -> {
                                val medicoActivity = Intent(this, MedicoActivity::class.java)
                                startActivity(medicoActivity)
                                finish()
                            }

                            "USUARIO" -> {
                                val userActivity = Intent(this, UserActivity::class.java)
                                startActivity(userActivity)
                                finish()
                            }

                            else -> Toast.makeText(this, "Rol no permitido", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Error de autenticacion",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

//    private fun showForgotPassword() {
//        startActivity(Intent(this, ForgotPasswordActivity::class.java))
//        finish()
//    }
}