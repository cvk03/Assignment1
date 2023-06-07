package com.example.assignment1

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestoreDB: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()
        firestoreDB = FirebaseFirestore.getInstance()

        // Initialize the QR Code scanner
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR Code")
        integrator.setCameraId(0) // Use the back camera
        integrator.setOrientationLocked(false)
        // Start the QR Code scanner
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if the QR Code scan was successful
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // Get the decoded value from the QR Code
            val decodedValue = result.contents
            Log.d("MainActivity", "Decoded value: $decodedValue")

            // Check if the user is authenticated
            val user = firebaseAuth.currentUser
            if (user != null) {
                // Save the decoded value to Firestore DB
                val document = firestoreDB.collection("users").document(user.uid)
                document.set(mapOf("qrCode" to decodedValue))
                Toast.makeText(this, "Code scanned and inforamtion saved successfully!!",Toast.LENGTH_LONG).show()
                startRegistrationActivity()
            } else {
                // Prompt the user to sign in or register
                // You can start a new activity or show a dialog to handle the authentication flow
                //Toast.makeText(this, "Please sign in or register", Toast.LENGTH_SHORT).show
                val user = firebaseAuth.currentUser
                if (user != null) {
                    // Save the decoded value to Firestore DB
                    val document = firestoreDB.collection("users").document(user.uid)
                    document.set(mapOf("qrCode" to decodedValue))
                } else {
                    // Prompt the user to sign in or register
                    // You can start a new activity or show a dialog to handle the authentication flow
                    //Toast.makeText(this, "Please sign in or register", Toast.LENGTH_SHORT).show()
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        // Save the decoded value to Firestore DB
                        val document = firestoreDB.collection("users").document(user.uid)
                        document.set(mapOf("qrCode" to decodedValue))
                    } else {
                        // Prompt the user to sign in or register
                        // You can start a new activity or show a dialog to handle the authentication flow
                    // Toast.makeText(this, "Please sign in or register", Toast.LENGTH_SHORT).show()
                        startRegistrationActivity()
                    }
                }
            }
        }
    }

    private fun startRegistrationActivity() {
        // Start the registration activity or perform the necessary logic to handle user registration
        val registrationIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(registrationIntent)
    }

}