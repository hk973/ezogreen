package com.example.ezogreen.Activity

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ezogreen.Name
import com.example.ezogreen.R
import com.google.firebase.auth.FirebaseAuth
import com.example.ezogreen.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private fun togglePasswordVisibility(passwordEditText: EditText, togglePassword: ImageView) {
        if (passwordEditText.transformationMethod == PasswordTransformationMethod.getInstance()) {
            // Show password
            passwordEditText.transformationMethod = null

            togglePassword.setImageResource(R.drawable.ic_openeye)
        } else {
            // Hide password
            passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            togglePassword.setImageResource(R.drawable.ic_closeeye)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val passwordEditText: EditText = findViewById(R.id.txtPassword)
        val togglePassword: ImageView = findViewById(R.id.togglePassword)

        togglePassword.setOnClickListener {
            togglePasswordVisibility(passwordEditText, togglePassword)
        }

        binding.txtSignUpNow.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLoginNow.setOnClickListener {
            val email = binding.txtEmailAddress.text.toString()
            val pass = binding.txtPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { loginTask ->
                    if (loginTask.isSuccessful) {
                        // Check if the logged-in user's email matches the entered email
                        val currentUser = firebaseAuth.currentUser
                        if (currentUser != null && currentUser.email == email) {
                            // Email matches, proceed to main activity
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                          var parts= email.split('@')
                            var substring=parts[0]
                            Name=substring

                        } else {
                            // Email doesn't match, sign out the unauthorized user
                            firebaseAuth.signOut()
                            Toast.makeText(
                                this,
                                "You are not authorized to log in with this email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Login failed
                        Toast.makeText(
                            this,
                            "Incorrect Fields or Check your Internet Connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                // Empty fields
                Toast.makeText(this, "Empty Fields are not Allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}