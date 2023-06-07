package com.example.assignment1

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var img_scan : ImageView
    private lateinit var btn_logout : Button

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        firebaseAuth = FirebaseAuth.getInstance()

        // Retrieve references to UI components
        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        registerButton = findViewById(R.id.btn_submit)
        img_scan = findViewById(R.id.img_scan)
        btn_logout = findViewById(R.id.btn_logout)

        // Set up click listener for the registration button
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Perform user registration
            registerUser(email, password)
        }
        img_scan.setOnClickListener{
            startQRCodeScanActivity()
        }
        btn_logout.setOnClickListener {
            firebaseAuth.signOut()
            Toast.makeText(this, "Successfully Loged Out!!",Toast.LENGTH_LONG).show()
        }
    }

    private fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    // You can navigate to the main activity or perform any other desired action

                } else {
                    // Registration failed
                    val exception = task.exception
                    Toast.makeText(this, "Registration failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun startQRCodeScanActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional: Finish the current activity to prevent the user from navigating back to it using the back button
    }
}