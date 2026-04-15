package com.example.universityeventmanagementapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val id: Int,
    val title: String,
    val date: String,
    val time: String,
    val venue: String,
    val category: String,
    val description: String,
    val price: Double,
    val totalSeats: Int,
    var availableSeats: Int,
    val imageRes: Int
) : Parcelable