

package com.example.photogalleryapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var adapter: PhotoAdapter
    lateinit var photos: MutableList<Photo>
    lateinit var selectionBar: LinearLayout
    lateinit var selectedCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridView = findViewById<GridView>(R.id.gridView)
        selectionBar = findViewById(R.id.selectionBar)
        selectedCount = findViewById(R.id.selectedCount)
        val deleteBtn = findViewById<Button>(R.id.deleteBtn)

        // sample images (drawable এ add করো)
        photos = mutableListOf(
            Photo(1, R.drawable.ic_launcher, "Nature1", "Nature"),
            Photo(2, R.drawable.ic_launcher, "City1", "City"),
            Photo(3, R.drawable.ic_launcher, "Animal1", "Animals"),
            Photo(4, R.drawable.ic_launcher, "Food1", "Food")
        )

        adapter = PhotoAdapter(this, photos)
        gridView.adapter = adapter

        // click
        gridView.setOnItemClickListener { _, _, pos, _ ->
            if (!adapter.selectionMode) {
                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("img", photos[pos].resourceId)
                startActivity(intent)
            } else {
                photos[pos].isSelected = !photos[pos].isSelected
                updateSelection()
            }
        }

        // long press
        gridView.setOnItemLongClickListener { _, _, pos, _ ->
            adapter.selectionMode = true
            selectionBar.visibility = LinearLayout.VISIBLE

            photos[pos].isSelected = true
            updateSelection()
            true
        }

        // delete
        deleteBtn.setOnClickListener {
            val before = photos.size
            photos.removeAll { it.isSelected }
            val deleted = before - photos.size

            Toast.makeText(this, "$deleted photos deleted", Toast.LENGTH_SHORT).show()

            adapter.selectionMode = false
            selectionBar.visibility = LinearLayout.GONE
            adapter.notifyDataSetChanged()
        }
    }

    fun updateSelection() {
        val count = photos.count { it.isSelected }
        selectedCount.text = "$count selected"
        adapter.notifyDataSetChanged()
    }
}