package com.example.ezogreen.Activity


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ezogreen.Data.Item
import com.example.ezogreen.Edittextvalue
import com.example.ezogreen.Name
import com.example.ezogreen.R
import com.example.ezogreen.id
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class BoardAdapter(private val context: Context, private val itemList: ArrayList<Item>): RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {
    private lateinit var db: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference


    class BoardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        var textview4: TextView = view.findViewById(R.id.textView4)
        var textView2: TextView = view.findViewById(R.id.textView2)
        var textView5: TextView = view.findViewById(R.id.textView5)
        var joinbtn : Button = view.findViewById(R.id.joinButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_viewrecyclerview, parent, false)
        return BoardViewHolder(view)


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val item = itemList[position]
        holder.textView.text = item.textView
        holder.textView2.text = item.textView1
        holder.textview4.text = item.textView2
        holder.textView5.text = item.textView3
        holder.joinbtn.setOnClickListener {
            val a = item.textView3.toString()






                if (a.contains(Name)) {
                    // Code to execute if the inputString contains the targetWord
                    println("The input string contains the target word.")
                   var k= MainActivity()

                    // Write your code here
                } else {
                    // Code to execute if the inputString does not contain the targetWord
                    println("The input string does not contain the target word.")
                    db = FirebaseDatabase.getInstance()
                    databaseReference = db.reference.child("Token").child(id).child("Field4")
                    databaseReference.setValue("$a,$Name")
                }




        }
        Picasso.get().load(item.imageview).into(holder.imageView)
    }

}
