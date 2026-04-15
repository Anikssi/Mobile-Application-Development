package com.example.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scrollView = findViewById<androidx.core.widget.NestedScrollView>(R.id.scrollView)
        val bookmarkBtn = findViewById<ImageButton>(R.id.bookmarkBtn)
        val shareBtn = findViewById<ImageButton>(R.id.shareBtn)

        val intro = findViewById<TextView>(R.id.intro)
        val key = findViewById<TextView>(R.id.key)
        val analysis = findViewById<TextView>(R.id.analysis)
        val conclusion = findViewById<TextView>(R.id.conclusion)

        // Navigation
        findViewById<Button>(R.id.btnIntro).setOnClickListener {
            scrollView.smoothScrollTo(0, intro.top)
        }

        findViewById<Button>(R.id.btnKey).setOnClickListener {
            scrollView.smoothScrollTo(0, key.top)
        }

        findViewById<Button>(R.id.btnAnalysis).setOnClickListener {
            scrollView.smoothScrollTo(0, analysis.top)
        }

        findViewById<Button>(R.id.btnConclusion).setOnClickListener {
            scrollView.smoothScrollTo(0, conclusion.top)
        }

        // Back to top
        findViewById<Button>(R.id.topBtn).setOnClickListener {
            scrollView.smoothScrollTo(0, 0)
        }

        // Bookmark toggle
        bookmarkBtn.setOnClickListener {
            isBookmarked = !isBookmarked

            if (isBookmarked) {
                bookmarkBtn.setImageResource(android.R.drawable.btn_star_big_on)
                Toast.makeText(this, "Article Bookmarked", Toast.LENGTH_SHORT).show()
            } else {
                bookmarkBtn.setImageResource(android.R.drawable.btn_star_big_off)
                Toast.makeText(this, "Bookmark Removed", Toast.LENGTH_SHORT).show()
            }
        }

        // Share
        shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Check this article: Future of AI")

            startActivity(Intent.createChooser(intent, "Share via"))
        }
    }
}