package com.deyvitineo.a7minuteworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_finish.*

class FinishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setSupportActionBar(toolbarFinishActivity)

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true) //set back button
        }

        toolbarFinishActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            finish()
        }
    }
}