package com.samrudhasolutions.parentalapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ezogreen.Activity.LoginActivity
import com.example.ezogreen.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileActivity : AppCompatActivity() {
    private lateinit var emailTextView: TextView
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var logOutButton: Button
    private lateinit var updateProfileButton: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

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

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        val passwordEditText: EditText = findViewById(R.id.editPassword)
        val togglePassword: ImageView = findViewById(R.id.togglePasswordR)

        // Get the current user
        currentUser = firebaseAuth.currentUser

        togglePassword.setOnClickListener {
            togglePasswordVisibility(passwordEditText, togglePassword)
        }

        // Find views in your layout
        emailTextView = findViewById(R.id.emailTextView)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        updateProfileButton = findViewById(R.id.updateProfileButton)
        logOutButton = findViewById(R.id.btnLogOut)
        firebaseAuth = FirebaseAuth.getInstance()

        logOutButton.setOnClickListener {
            fun logout() {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            logout()

        }

        // Set initial user information
        updateUI(currentUser)

        // Set click listener for the Update Profile button
        updateProfileButton.setOnClickListener {
            val newEmail = editEmail.text.toString().trim()
            val newPassword = editPassword.text.toString().trim()

            if (newEmail.isNotEmpty() && newPassword.isNotEmpty()) {
                updateEmailAndPassword(newEmail, newPassword)
            } else {
                Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            emailTextView.text = "Email: ${user.email}"
            editEmail.setText(user.email)
        }
    }

    private fun updateEmailAndPassword(newEmail: String, newPassword: String) {
        currentUser?.updateEmail(newEmail)?.addOnCompleteListener { emailTask ->
                if (emailTask.isSuccessful) {
                    currentUser?.updatePassword(newPassword)
                    currentUser?.updateEmail(newEmail)?.addOnCompleteListener { passwordTask ->
                            if (passwordTask.isSuccessful) {
                                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT)
                                    .show()
                                updateUI(currentUser)
                            } else {
                                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            if (emailTask.isSuccessful) {
                                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT)
                                    .show()
                                updateUI(currentUser)

                            } else {
                                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT)
                                    .show()

                            }
                        }
                } else {
                    Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }

}