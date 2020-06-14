package com.deyvitineo.a7minuteworkout

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    private var isMetric = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(toolbarBmiActivity)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Calculate BMI"
        }

        toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculate.setOnClickListener {
            if (isMetric) {
                if (validateMetricUnits()) {
                    val height = etHeightInput.text.toString().toFloat() / 100 //converts to meters
                    val weight = etWeightInput.text.toString().toFloat()
                    val bmi = calculateBmi(height, weight)
                    tvResult.text = ""
                    displayBmiResults(bmi, tvResult)
                } else {
                    Toast.makeText(this, "Please fill out all the information", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                if (validateImperialUnits()) {
                    val heightFeet = etHeightFeet.text.toString()
                    val heightInches = etHeightFeet.text.toString()
                    val weight = etWeightInput.text.toString().toFloat()
                    val finalHeight = heightInches.toFloat() + (heightFeet.toFloat() * 12)
                    val bmi = calculateBmi(finalHeight, weight)
                    tvResult.text = ""
                    displayBmiResults(bmi, tvResult)
                } else {
                    Toast.makeText(this, "Please fill out all the information", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

        etWeightInput.hint = "WEIGHT (in kg)"
        rgMetricSystem.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rBtnMetric) {
                metricSystem()
            } else {
                imperialSystem()
            }
        }

    }

    private fun metricSystem() {
        isMetric = true
        etWeightInput.setText("")
        tvResult.text = ""
        etHeightInput.setText("")
        etHeightFeet.setText("")
        etHeightInches.setText("")
        etWeightInput.hint = "WEIGHT (in kg)"
        tvHeightMetric.visibility = View.VISIBLE
        llImperialHeight.visibility = View.GONE
    }

    private fun imperialSystem() {
        isMetric = false
        etWeightInput.setText("")
        tvResult.text = ""
        tvResult.text = ""
        etHeightInput.setText("")
        etHeightFeet.setText("")
        etHeightInches.setText("")
        etWeightInput.hint = "WEIGHT (in lbs)"
        tvHeightMetric.visibility = View.GONE
        llImperialHeight.visibility = View.VISIBLE
    }

    private fun displayBmiResults(bmi: Float, textView: TextView) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        textView.append("Your bmi is: $bmiValue\n")
        textView.append(bmiLabel + "\n")
        textView.append(bmiDescription)
    }

    private fun calculateBmi(height: Float, weight: Float): Float {
        return if (isMetric) {
            weight / (height * height)
        } else {
            (703 * weight) / (height * height)
        }
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (etWeightInput.text.toString().isEmpty()) {
            isValid = false
        } else if (etHeightInput.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun validateImperialUnits(): Boolean {
        var isValid = true
        if (etWeightInput.text.toString().isEmpty()) {
            isValid = false
        } else if (etHeightFeet.text.toString().isEmpty() || etHeightInches.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }
}