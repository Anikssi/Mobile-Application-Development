package com.example.universityeventmanagementapp

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_event_detail)

    // View gulo initialize kora
    val tvTitle = findViewById<TextView>(R.id.tvTitle)
    val tvDescription = findViewById<TextView>(R.id.tvDescription)


    val event = intent.getParcelableExtra<Event>("EVENT_DATA")

    // 2. Data jodi null na hoy, tobe UI te set kora
    event?.let {
        tvTitle.text = it.title
        tvDescription.text = it.description
        // Venue, Date, Time ityadi ekhanei set korben
        // findViewById<TextView>(R.id.tvVenue).text = it.venue
    }
}