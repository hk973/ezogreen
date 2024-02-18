package com.example.ezogreen.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ezogreen.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.btnRegister.setOnClickListener {
            val email = binding.txtRegisterEmailAddress.text.toString()
            val pass = binding.txtRegisterPass.text.toString()
            val confirmPass = binding.txtRegisterConfirmPass.text.toString()
            val stuName = binding.txtRegisterStuName.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()
                && stuName.isNotEmpty() && email.isNotEmpty()
            ) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { registrationTask ->
                            if (registrationTask.isSuccessful) {

                                Toast.makeText(this , "Registeration Successful",Toast.LENGTH_SHORT).show()

                            }else {
                                Toast.makeText(
                                    this,
                                    "Registration failed. Check your Internet Connection",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields are not Allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Example function to determine the user role based on your app logic
    private fun determineUserRole(): String {
        return "Parental App"
    }
}
