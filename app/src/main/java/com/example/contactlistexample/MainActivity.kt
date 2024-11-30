package com.example.contactlistexample

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexample.adapter.ContactAdapter
import com.example.contactlistexample.data.Contact
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Form elements find
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone= findViewById<EditText>(R.id.etPhone)
        val checkbox = findViewById<CheckBox>(R.id.checkbox)
        val btnAddContact = findViewById<Button>(R.id.addButton)
        val activityFilter = findViewById<FloatingActionButton>(R.id.activityFilter)
        setRecyclerViewAdapter(contactList)

        btnAddContact.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val isAvailable = checkbox.isChecked

            if(name.isEmpty() || phone.isEmpty() ){
                Toast.makeText(this, "Please, enter all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newContact=Contact(name, phone, isAvailable)

            contactList.add(newContact)
            setRecyclerViewAdapter(contactList)

            etName.text.clear()
            etPhone.text.clear()
            checkbox.isChecked= false
            Toast.makeText(this, "Contact added succesfully", Toast.LENGTH_SHORT).show()

        }

        var isFiltered = false
        activityFilter.setOnClickListener{
            if (!isFiltered) {

                val filteredList = contactList.filter { contact -> contact.isAvailable }
                if (filteredList.isEmpty()) {
                    Toast.makeText(this, "No contacts available", Toast.LENGTH_SHORT).show()
                } else {
                    setRecyclerViewAdapter(filteredList)
                    Toast.makeText(this, "Showing available contacts", Toast.LENGTH_SHORT).show()
                }
                isFiltered = true
            } else {

                setRecyclerViewAdapter(contactList)
                Toast.makeText(this, "Showing all contacts", Toast.LENGTH_SHORT).show()
                isFiltered = false
            }
        }



    }

    // RecyclerView
    private fun setRecyclerViewAdapter(contactList: List<Contact>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ContactAdapter(contactList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

}