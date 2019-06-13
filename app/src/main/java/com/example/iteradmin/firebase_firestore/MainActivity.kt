package com.example.iteradmin.firebase_firestore

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = FirebaseFirestore.getInstance()
        val name = findViewById<EditText>(R.id.name)
        val branch = findViewById<EditText>(R.id.branch)
        val city = findViewById<EditText>(R.id.city)
        val save = findViewById<Button>(R.id.save)
        val load = findViewById<Button>(R.id.load)
        val data = findViewById<TextView>(R.id.data)
        save.setOnClickListener {
            val n = name.text.toString()
            val b = branch.text.toString()
            val c = city.text.toString()
            val map = hashMapOf(
                    "name" to n,
                    "branch" to  b,
                    "city" to c
            )
            db.collection("users")
                    .add(map)
                    .addOnSuccessListener {
                        Toast.makeText(this,"add to firestore",Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"wrong",Toast.LENGTH_LONG).show()
                    }

        }
        load.setOnClickListener {
            db.collection("users")
                    .get()
                    .addOnSuccessListener {querySnapshot->
                        for (d in querySnapshot.documents){
                            val city:String = d.data?.get("city").toString()
                            Toast.makeText(this,city,Toast.LENGTH_LONG).show()
                        }

                    }
                    .addOnFailureListener {
                        Toast.makeText(this,"error",Toast.LENGTH_LONG).show()
                    }
        }
    }
}
