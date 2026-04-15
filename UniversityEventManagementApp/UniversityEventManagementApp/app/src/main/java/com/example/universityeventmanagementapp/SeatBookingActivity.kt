package com.example.universityeventmanagementapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SeatBookingActivity : AppCompatActivity() {

    private val totalRows = 8
    private val totalCols = 6
    private var selectedSeatsCount = 0
    private val ticketPrice = 500.0 // Example price

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_booking)

        val gridLayout = findViewById<GridLayout>(R.id.seatGrid)
        val tvSummary = findViewById<TextView>(R.id.tvSummary)

        // Generate Seats
        for (i in 0 until (totalRows * totalCols)) {
            val seat = Button(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 100
                    height = 100
                    setMargins(8, 8, 8, 8)
                }
                text = "${i + 1}"
                textSize = 10sp

                // Simulate pre-booked seats (~30%)
                val isPreBooked = (0..100).random() < 30
                if (isPreBooked) {
                    setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
                    isEnabled = false
                } else {
                    setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
                    tag = "available"
                }

                setOnClickListener {
                    if (tag == "available") {
                        setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_light))
                        tag = "selected"
                        selectedSeatsCount++
                    } else if (tag == "selected") {
                        setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
                        tag = "available"
                        selectedSeatsCount--
                    }
                    tvSummary.text = "Selected: $selectedSeatsCount | Total: $${selectedSeatsCount * ticketPrice}"
                }
            }
            gridLayout.addView(seat)
        }
    }

    override fun onBackPressed() {
        if (selectedSeatsCount > 0) {
            AlertDialog.Builder(this)
                .setTitle("Discard Selection?")
                .setMessage("You have selected seats. Are you sure you want to go back?")
                .setPositiveButton("Yes") { _, _ -> super.onBackPressed() }
                .setNegativeButton("No", null)
                .show()
        } else {
            super.onBackPressed()
        }
    }
}