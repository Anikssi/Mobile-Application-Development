
package com.example.contactbookapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var adapter: ContactAdapter
    lateinit var contactList: MutableList<Contact>
    lateinit var emptyView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        emptyView = findViewById(R.id.emptyView)
        val searchView = findViewById<SearchView>(R.id.searchView)
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        contactList = mutableListOf()

        adapter = ContactAdapter(this, contactList)
        listView.adapter = adapter

        // Empty view
        listView.emptyView = emptyView

        // FAB - Add contact
        fab.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_contact, null)

            val name = dialogView.findViewById<EditText>(R.id.nameInput)
            val phone = dialogView.findViewById<EditText>(R.id.phoneInput)
            val email = dialogView.findViewById<EditText>(R.id.emailInput)

            AlertDialog.Builder(this)
                .setTitle("Add Contact")
                .setView(dialogView)
                .setPositiveButton("Add") { _, _ ->
                    contactList.add(
                        Contact(
                            name.text.toString(),
                            phone.text.toString(),
                            email.text.toString()
                        )
                    )
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Click
        listView.setOnItemClickListener { _, _, position, _ ->
            val c = contactList[position]
            Toast.makeText(this, "${c.name}\n${c.phone}\n${c.email}", Toast.LENGTH_LONG).show()
        }

        // Long press delete
        listView.setOnItemLongClickListener { _, _, position, _ ->
            AlertDialog.Builder(this)
                .setTitle("Delete?")
                .setPositiveButton("Yes") { _, _ ->
                    contactList.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("No", null)
                .show()
            true
        }

        // Search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }
}