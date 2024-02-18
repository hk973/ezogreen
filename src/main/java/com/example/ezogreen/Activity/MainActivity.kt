package com.example.ezogreen.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ezogreen.Data.Item
import com.example.ezogreen.Name
import com.example.ezogreen.R
import com.example.ezogreen.id
import com.example.ezogreen.otherclasses.MarginItemDecoration
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerdashboard: RecyclerView
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dataref: DatabaseReference
    private lateinit var button: Button
    private lateinit var itemList: ArrayList<Item>
    private lateinit var recyclerAdapter: BoardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerdashboard = findViewById(R.id.recyclerview)
        val itemDecoration = MarginItemDecoration(
            R.dimen.margin_size,
            this
        ) // R.dimen.margin_size is the dimension resource for margin size
        recyclerdashboard.addItemDecoration(itemDecoration)
        button = findViewById(R.id.nextButton)

        button.setOnClickListener {
            val intent = Intent(this, InputField::class.java)
            startActivity(intent)
        }

        itemList = ArrayList()
        val layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerAdapter = BoardAdapter(this@MainActivity, itemList)
        recyclerdashboard.adapter = recyclerAdapter
        recyclerdashboard.layoutManager = layoutManager

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference().child("Token")

        // Add a listener to retrieve data from Firebase
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                itemList.clear()
                for (snapshot in dataSnapshot.children) {
                     id = snapshot.key.toString()
                    val field1 = snapshot.child("Field1").getValue(String::class.java)
                    val field2 = snapshot.child("Field2").getValue(String::class.java)
                    val field3 = snapshot.child("Field3").getValue(String::class.java)
                    val field4 = snapshot.child("Field4").getValue(String::class.java)
                    val imageURL = snapshot.child("ImageURL").getValue(String::class.java)


                    // Create an Item object with retrieved data
                    val item = Item(field1, field2, field3, field4, imageURL)
                    itemList.add(item)
                }
                // Notify adapter about the changes
                recyclerAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        })





    }




}
