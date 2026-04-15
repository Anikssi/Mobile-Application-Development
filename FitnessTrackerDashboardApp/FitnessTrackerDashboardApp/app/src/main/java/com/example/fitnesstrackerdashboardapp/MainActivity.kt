package com.example.fitnesstrackerapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var stepsText: TextView
    lateinit var progressBar: ProgressBar
    lateinit var percentText: TextView
    lateinit var updateBtn: Button

    var steps = 0
    val goal = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stepsText = findViewById(R.id.stepsText)
        progressBar = findViewById(R.id.progressBar)
        percentText = findViewById(R.id.percentText)
        updateBtn = findViewById(R.id.updateBtn)

        updateBtn.setOnClickListener {
            val input = EditText(this)

            AlertDialog.Builder(this)
                .setTitle("Enter Steps")
                .setView(input)
                .setPositiveButton("OK") { _, _ ->
                    val newSteps = input.text.toString().toIntOrNull()

                    if (newSteps != null) {
                        steps = newSteps
                        stepsText.text = steps.toString()

                        val percent = (steps * 100) / goal
                        progressBar.progress = percent
                        percentText.text = "$percent%"

                        if (percent >= 100) {
                            Toast.makeText(this, "Goal Achieved! 🎉", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}