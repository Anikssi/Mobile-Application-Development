package com.example.gradereportapp

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var tableLayout: TableLayout
    lateinit var subName: EditText
    lateinit var obtMarks: EditText
    lateinit var totalMarks: EditText
    lateinit var summaryText: TextView
    lateinit var gpaText: TextView

    var totalSubjects = 0
    var passed = 0
    var failed = 0
    var totalGpa = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tableLayout = findViewById(R.id.tableLayout)
        subName = findViewById(R.id.subName)
        obtMarks = findViewById(R.id.obtMarks)
        totalMarks = findViewById(R.id.totalMarks)
        summaryText = findViewById(R.id.summaryText)
        gpaText = findViewById(R.id.gpaText)
        val addBtn = findViewById<Button>(R.id.addBtn)

        addBtn.setOnClickListener {

            val name = subName.text.toString()
            val obt = obtMarks.text.toString().toIntOrNull()
            val total = totalMarks.text.toString().toIntOrNull()

            if (name.isEmpty() || obt == null || total == null) {
                Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val percent = (obt * 100) / total

            val (grade, gpa) = getGrade(percent)

            totalSubjects++
            totalGpa += gpa

            if (grade == "F") failed++ else passed++

            val row = TableRow(this)

            val tv1 = TextView(this)
            val tv2 = TextView(this)
            val tv3 = TextView(this)
            val tv4 = TextView(this)

            tv1.text = name
            tv2.text = obt.toString()
            tv3.text = total.toString()
            tv4.text = grade

            row.addView(tv1)
            row.addView(tv2)
            row.addView(tv3)
            row.addView(tv4)

            // Color highlight
            if (grade == "F") {
                row.setBackgroundColor(Color.RED)
            } else {
                row.setBackgroundColor(Color.GREEN)
            }

            tableLayout.addView(row)

            // Update summary
            summaryText.text = "Total: $totalSubjects  Passed: $passed  Failed: $failed"

            // Update GPA
            val finalGpa = totalGpa / totalSubjects
            gpaText.text = "GPA: %.2f".format(finalGpa)

            subName.text.clear()
            obtMarks.text.clear()
            totalMarks.text.clear()
        }
    }

    fun getGrade(percent: Int): Pair<String, Double> {
        return when (percent) {
            in 90..100 -> Pair("A+", 4.0)
            in 80..89 -> Pair("A", 3.7)
            in 70..79 -> Pair("B+", 3.3)
            in 60..69 -> Pair("B", 3.0)
            in 50..59 -> Pair("C", 2.0)
            in 40..49 -> Pair("D", 1.0)
            else -> Pair("F", 0.0)
        }
    }
}