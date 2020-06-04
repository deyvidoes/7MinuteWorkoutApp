package com.deyvitineo.a7minuteworkout

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.deyvitineo.a7minuteworkout.adapters.ExerciseStatusAdapter
import com.deyvitineo.a7minuteworkout.util.Constants
import com.deyvitineo.a7minuteworkout.util.Exercises
import kotlinx.android.synthetic.main.activity_exercise.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseProgress = 0

    private var mExerciseList: ArrayList<ExerciseModel>? = null
    private var mCurrentExercisePosition = -1

    private var textToSpeech: TextToSpeech? = null
    private val TAG: String? = ExerciseActivity::class.simpleName
    private var mPlayer: MediaPlayer? = null

    private lateinit var mExerciseAdapter: ExerciseStatusAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        textToSpeech = TextToSpeech(this, this)
        mExerciseList = Exercises.defaultExerciseList()
        setupRecyclerView()
        setupRestView()

    }


    private fun setupRestView() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setupExerciseView() {
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
        iv_exercise_image.setImageResource(mExerciseList!![mCurrentExercisePosition].image)
        tv_exercise_name.text = mExerciseList!![mCurrentExercisePosition].name
    }

    //set up the resting view and timer
    private fun setRestProgressBar() {
        mCurrentExercisePosition++
        rest_progress_bar.progress = restProgress
        rest_progress_bar.max = (Constants.REST_HARD / Constants.SECOND_IN_MILLIS).toInt()
        var exerciseName = "Get ready for : ${mExerciseList!![mCurrentExercisePosition].name}"
        tv_rest_exercise_name.text = exerciseName
        speak(exerciseName)

        restTimer = object : CountDownTimer(Constants.REST_HARD, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                val timeLeft = (Constants.REST_HARD / Constants.SECOND_IN_MILLIS) - restProgress
                rest_progress_bar.progress = timeLeft.toInt()
                tv_rest_timer.text = timeLeft.toString()
            }

            override fun onFinish() {
                ll_rest_view.visibility = View.GONE
                ll_exercise_view.visibility = View.VISIBLE

                mExerciseList!![mCurrentExercisePosition].isSelected = true
                mExerciseAdapter.notifyDataSetChanged()
                setupExerciseView()

            }
        }.start()
    }

    private fun setExerciseProgressBar() {
        exercise_progress_bar.progress = exerciseProgress
        exercise_progress_bar.max = (Constants.EXERCISE_MEDIUM / Constants.SECOND_IN_MILLIS).toInt()

        exerciseTimer = object : CountDownTimer(Constants.EXERCISE_MEDIUM, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                val timeLeft =
                    (Constants.EXERCISE_MEDIUM / Constants.SECOND_IN_MILLIS) - exerciseProgress
                exercise_progress_bar.progress = timeLeft.toInt()
                tv_exercise_timer.text = timeLeft.toString()

                if (timeLeft in 1..5) {
                    speak(timeLeft.toString())
                }
                if (timeLeft == 0L) playExerciseFinishedSound()
            }

            override fun onFinish() {
                if (mCurrentExercisePosition < mExerciseList!!.size - 1) {
                    mExerciseList!![mCurrentExercisePosition].isSelected = false
                    mExerciseList!![mCurrentExercisePosition].isCompleted = true
                    mExerciseAdapter.notifyDataSetChanged()

                    ll_rest_view.visibility = View.VISIBLE
                    ll_exercise_view.visibility = View.GONE
                    setupRestView()
                } else {
                    Toast.makeText(this@ExerciseActivity, "Exercises finished", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }.start()
    }

    private fun speak(text: String) {
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun playExerciseFinishedSound() {
        try {
            mPlayer = MediaPlayer.create(applicationContext, R.raw.press_start)
            mPlayer!!.isLooping = false
            mPlayer!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "setupRestView: ", e)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            var result = textToSpeech!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onInit: error: language not supported or missing data ")
            }
        } else {
            Log.w(TAG, "onInit: Something went wrong. STATUS -> $status")
        }
    }

    private fun setupRecyclerView(){
        rv_exercise_status.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mExerciseAdapter = ExerciseStatusAdapter(mExerciseList!!, this)
        rv_exercise_status.adapter = mExerciseAdapter
    }

    override fun onDestroy() {
        if (restTimer != null) restTimer!!.cancel()
        if (exerciseTimer != null) exerciseTimer!!.cancel()

        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }

        if (mPlayer != null) {
            mPlayer!!.stop()
        }
        super.onDestroy()
    }

}
