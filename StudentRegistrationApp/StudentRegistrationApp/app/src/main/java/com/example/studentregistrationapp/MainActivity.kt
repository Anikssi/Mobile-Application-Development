package com.example.studentregistrationapp   // Change if your package name is different

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var studentIdEditText: EditText
    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var footballCheckBox: CheckBox
    private lateinit var cricketCheckBox: CheckBox
    private lateinit var basketballCheckBox: CheckBox
    private lateinit var badmintonCheckBox: CheckBox
    private lateinit var countrySpinner: Spinner
    private lateinit var dobButton: Button
    private lateinit var submitButton: Button
    private lateinit var resetButton: Button

    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        studentIdEditText = findViewById(R.id.studentIdEditText)
        fullNameEditText = findViewById(R.id.fullNameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        ageEditText = findViewById(R.id.ageEditText)
        genderRadioGroup = findViewById(R.id.genderRadioGroup)
        footballCheckBox = findViewById(R.id.footballCheckBox)
        cricketCheckBox = findViewById(R.id.cricketCheckBox)
        basketballCheckBox = findViewById(R.id.basketballCheckBox)
        badmintonCheckBox = findViewById(R.id.badmintonCheckBox)
        countrySpinner = findViewById(R.id.countrySpinner)
        dobButton = findViewById(R.id.dobButton)
        submitButton = findViewById(R.id.submitButton)
        resetButton = findViewById(R.id.resetButton)

        // Setup Spinner
        val countries = listOf("Bangladesh", "India", "USA", "UK", "Canada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.adapter = adapter

        // Date Picker
        dobButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                dobButton.text = selectedDate
            }, year, month, day).show()
        }

        // Submit Button
        submitButton.setOnClickListener {
            val studentId = studentIdEditText.text.toString().trim()
            val fullName = fullNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val ageStr = ageEditText.text.toString().trim()

            // Gender
            val selectedGenderId = genderRadioGroup.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else ""

            // Favorite Sports
            val sportsList = mutableListOf<String>()
            if (footballCheckBox.isChecked) sportsList.add("Football")
            if (cricketCheckBox.isChecked) sportsList.add("Cricket")
            if (basketballCheckBox.isChecked) sportsList.add("Basketball")
            if (badmintonCheckBox.isChecked) sportsList.add("Badminton")
            val sports = if (sportsList.isEmpty()) "None" else sportsList.joinToString(", ")

            val country = countrySpinner.selectedItem.toString()
            val dob = selectedDate

            val age = ageStr.toIntOrNull() ?: 0

            // Validation
            if (studentId.isEmpty() || fullName.isEmpty() || email.isEmpty() || password.isEmpty() ||
                ageStr.isEmpty() || gender.isEmpty() || dob.isEmpty() || age <= 0 || !email.contains("@")) {

                Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Success Toast with all data
            val message = """
                ID: $studentId
                Name: $fullName
                Email: $email
                Password: $password
                Age: $age
                Gender: $gender
                Sports: $sports
                Country: $country
                DOB: $dob
            """.trimIndent()

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        // Reset Button
        resetButton.setOnClickListener {
            studentIdEditText.setText("")
            fullNameEditText.setText("")
            emailEditText.setText("")
            passwordEditText.setText("")
            ageEditText.setText("")

            genderRadioGroup.clearCheck()

            footballCheckBox.isChecked = false
            cricketCheckBox.isChecked = false
            basketballCheckBox.isChecked = false
            badmintonCheckBox.isChecked = false

            countrySpinner.setSelection(0)

            selectedDate = ""
            dobButton.text = "Select Date of Birth"
        }
    }
}