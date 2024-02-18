package com.example.ezogreen.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ezogreen.Name
import com.example.ezogreen.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class InputField : AppCompatActivity() {

    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText
    private lateinit var editText4: TextView
    private lateinit var button: Button
    private lateinit var db: FirebaseDatabase
    private lateinit var storage: FirebaseStorage

    private var selectedImageUri: Uri? = null
    private lateinit var imageView: ImageView
    private lateinit var buttonImage: Button
    private lateinit var imageView1: ImageView

    private lateinit var storageRef: StorageReference


    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
                imageView1.setImageURI(selectedImageUri)
            }
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_field1)


        // Initialize Firebase database
        db = FirebaseDatabase.getInstance()

        // Initialize Firebase storage
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        editText1 = findViewById(R.id.editText1)
        buttonImage = findViewById(R.id.upload)
        button = findViewById(R.id.uploadButton)
        editText2 = findViewById(R.id.editText2)
        editText3 = findViewById(R.id.editText3)
        editText4 = findViewById(R.id.editText4)
        imageView1 = findViewById(R.id.imageView1)



        button.setOnClickListener {

            uploadData()

        }

        buttonImage.setOnClickListener {
            selectImage()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        launcher.launch(intent)
    }


    private fun uploadData() {
        // Get text from EditText fields
        val text1 = editText1.text.toString()
        val text2 = editText2.text.toString()
        val text3 = editText3.text.toString()
        val text4 = editText4.text.toString()

        // Create a reference to the Firebase database root node
        val databaseReference = db.reference

        // Push a new node to the database with the EditText values
        val newDataRef = databaseReference.child("Token").push()
        newDataRef.child("Field1").setValue(text1)
        newDataRef.child("Field2").setValue(text2)
        newDataRef.child("Field3").setValue(text3)
        newDataRef.child("Field4").setValue(text4)

        // Upload image to Firebase Storage
        selectedImageUri?.let { uri ->
            val imageName = UUID.randomUUID().toString()
            val imageRef = storageRef.child("images/$imageName")
            imageRef.putFile(uri)
                .addOnSuccessListener {
                    // Image upload successful, get the download URL
                    imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        // Save the download URL in the database
                        newDataRef.child("ImageURL").setValue(downloadUri.toString())
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle image upload failure
                    // You can add appropriate error handling here
                }
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST_CODE = 100
    }
}
