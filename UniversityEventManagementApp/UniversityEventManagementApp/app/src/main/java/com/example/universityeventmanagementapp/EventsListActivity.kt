package com.example.universityeventmanagementapp

val intent = Intent(this, EventDetailActivity::class.java).apply {
    putExtra("EVENT_DATA", selectedEvent) // selectedEvent is an instance of Event.kt
}
startActivity(intent)