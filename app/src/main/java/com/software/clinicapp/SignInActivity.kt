package com.software.clinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.software.clinicapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    companion object {
        const val AUTH_COLLECTION = "auth"
        const val USERS_COLLECTION = "users"
        const val ADMIN_COLLECTION = "admins"
        const val MEDICS_COLLECTION = "medics"
        const val MIN_PASSWORD_LENGTH = 6
    }

    private lateinit var binding: ActivitySignInBinding
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val callback = object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            val homeIntent = Intent(this@SignInActivity, MainActivity::class.java)
            startActivity(homeIntent)
            finish()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, callback)

        val name = binding.etRealName
        val username = binding.etNickName
        val email = binding.etEmail
        val password = binding.etPassword
        val passwordConfirmation = binding.etRepeatPassword
        val roleUser = "USUARIO" //parte secreta chamo

        binding.btnCreateAccount.setOnClickListener {
            if (name.text!!.isNotEmpty() && username.text!!.isNotEmpty() &&
                email.text!!.isNotEmpty() && password.text!!.isNotEmpty() &&
                passwordConfirmation.text!!.isNotEmpty()
            ) {

                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            //sendEmailVerification()
                            insertUserFromDB(
                                name.text.toString(),
                                username.text.toString(),
                                email.text.toString(),
                                roleUser
                            )
                            addUserFromAuth(email.text.toString(), roleUser)
                            showHome()
                        } else showAlert()
                    }
            } else {
                Toast.makeText(this, "No pueden haber campos en blanco", Toast.LENGTH_LONG).show()
            }

        }


    }

    /*private fun sendEmailVerification(){
        val user = auth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener {
            if(it.isSuccessful){

            } else {

            }
        }
    }*/

    private fun insertUserFromDB(
        name: String, username: String, email: String, role: String
    ) {
        if (isValidName(name) && isValidName(username) && isValidOrEmptyEmail(email) &&
            isValidRole(role)
        ) {
            val user = hashMapOf(
                "name" to name,
                "username" to username,
                "email" to email,
                "rol" to role
            )

            when (role) {
                "ADMIN" -> {
                    db.collection(ADMIN_COLLECTION).add(user).addOnSuccessListener {
                        Toast.makeText(this, "Bienvenido Administrador", Toast.LENGTH_LONG).show()
                    }
                }

                "USUARIO" -> {
                    db.collection(USERS_COLLECTION).add(user).addOnSuccessListener {
                        Toast.makeText(this, "Bienvenido Usuario", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    private fun addUserFromAuth(email: String, role: String) {
        if (isValidOrEmptyEmail(email) && isValidRole(role)){
            val userAuth = hashMapOf(
                "email" to email,
                "rol" to role
            )
            db.collection(AUTH_COLLECTION).add(userAuth).addOnSuccessListener {}
        }
    }

    private fun isValidName(name: String): Boolean =
        name.length >= MIN_PASSWORD_LENGTH || name.isEmpty()

    private fun isValidOrEmptyEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()

    private fun isValidOrEmptyPassword(password: String, passwordConfirmation: String): Boolean =
        (password.length >= MIN_PASSWORD_LENGTH && password == passwordConfirmation) ||
                password.isEmpty() || passwordConfirmation.isEmpty()

    private fun isValidRole(roleUser: String) =
        (roleUser == "ADMIN") || (roleUser == "MEDICO") || (roleUser == "USUARIO") || roleUser.isEmpty()


    private fun showHome() {
        Toast.makeText(this, "Te has registrado exitosamente", Toast.LENGTH_LONG).show()
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al registrar el usuario.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}