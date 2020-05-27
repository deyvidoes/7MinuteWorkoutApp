package com.deyvitineo.a7minuteworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.deyvitineo.a7minuteworkout.util.Constants

class ExerciseActivity : AppCompatActivity() {

    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mTimerTextView: TextView

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        initWidgets()
        initActionBar()
        setupRestView()
    }

    private fun initWidgets() {
        mToolbar = findViewById(R.id.toolbar_exercise_activity)
        mProgressBar = findViewById(R.id.exercise_progress_bar)
        mTimerTextView = findViewById(R.id.tv_timer)
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

    private fun setRestProgressBar(){
        mProgressBar.progress = restProgress

        restTimer = object : CountDownTimer(Constants.EXERCISE_EASY, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                var timeLeft = (Constants.EXERCISE_EASY / Constants.SECOND_IN_MILLIS) - restProgress
                mProgressBar.progress = timeLeft.toInt()
                mTimerTextView.text = timeLeft.toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Time has finished", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        if(restTimer != null){
            restTimer!!.cancel()
        }
        super.onDestroy()
    }

    private fun setupRestView(){
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }
}
