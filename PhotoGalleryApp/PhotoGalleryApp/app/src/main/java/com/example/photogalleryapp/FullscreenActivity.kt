package com.example.photogalleryapp



import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        val img = findViewById<ImageView>(R.id.fullImage)
        val resId = intent.getIntExtra("img", 0)

        img.setImageResource(resId)
    }
}