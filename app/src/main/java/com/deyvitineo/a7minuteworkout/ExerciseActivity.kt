package com.deyvitineo.a7minuteworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ExerciseActivity : AppCompatActivity() {

    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        initWidgets()
        initActionBar()
    }

    private fun initWidgets() {
        mToolbar = findViewById(R.id.toolbar_exercise_activity)
    }

    private fun initActionBar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initListeners() {

    }
}
