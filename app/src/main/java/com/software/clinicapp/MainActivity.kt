package com.software.clinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.software.clinicapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    companion object{
        const val AUTH_COLLECTION = "auth"
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = auth.currentUser
        if (currentUser != null){
            redirectToUserRole(currentUser)
        }
        setup()
    }


    private fun setup(){
        binding.btnLogin.setOnClickListener { goToLogin() }
        binding.btnSignIn.setOnClickListener { goToSignIn() }
    }

    private fun goToLogin(){
        val loginIntent = Intent(this, LogInActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    private fun goToSignIn(){
        val signInIntent = Intent(this, SignInActivity::class.java)
        startActivity(signInIntent)
        finish()
    }

    private fun redirectToUserRole(user: FirebaseUser){
        val userId = user.email

        val db = FirebaseFirestore.getInstance()
        val usersRef = db.collection(AUTH_COLLECTION)

        usersRef.document(userId!!).get()
            .addOnSuccessListener { document ->
                if (document.exists()){
                    val userData = document.data
                    when(userData!!["Rol"] as String){
                        "ADMIN" -> {
                            startActivity(Intent(this, AdminActivity::class.java))
                            finish()
                        }

                        "MEDICO" -> {
                            startActivity(Intent(this, MedicoActivity::class.java))
                            finish()
                        }

                        "USUARIO" -> {
                            startActivity(Intent(this, UserActivity::class.java))
                            finish()
                        }
                    }

                }
            }
    }

}