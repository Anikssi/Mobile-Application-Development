package com.example.loginprofileapp

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var loginBtn: Button
    lateinit var logoutBtn: Button
    lateinit var progressBar: ProgressBar
    lateinit var profileCard: LinearLayout
    lateinit var forgot: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginBtn = findViewById(R.id.loginBtn)
        logoutBtn = findViewById(R.id.logoutBtn)
        progressBar = findViewById(R.id.progressBar)
        profileCard = findViewById(R.id.profileCard)
        forgot = findViewById(R.id.forgot)

        // Login Button
        loginBtn.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (user == "admin" && pass == "1234") {
                progressBar.visibility = View.VISIBLE

                Handler().postDelayed({
                    progressBar.visibility = View.GONE
                    profileCard.visibility = View.VISIBLE

                    username.visibility = View.GONE
                    password.visibility = View.GONE
                    loginBtn.visibility = View.GONE
                    forgot.visibility = View.GONE
                }, 2000)

            } else {
                Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show()
            }
        }

        // Logout Button
        logoutBtn.setOnClickListener {
            profileCard.visibility = View.GONE

            username.visibility = View.VISIBLE
            password.visibility = View.VISIBLE
            loginBtn.visibility = View.VISIBLE
            forgot.visibility = View.VISIBLE

            username.text.clear()
            password.text.clear()
        }

        // Forgot Password
        forgot.setOnClickListener {
            Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_LONG).show()
        }
    }
}