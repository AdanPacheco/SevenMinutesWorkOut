package com.udemy.sevenminutesworkout.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.udemy.sevenminutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiBinding
    private var isMetricsSelected: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActionBar()
        binding.btnCalculate.setOnClickListener {
            calculateBMI()
        }

        binding.rgUnits.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == binding.rbMetricsUnits.id) {
                makeMetricUnitsVisible()
            } else {
                makeUsUnitsVisible()
            }
        }
    }

    private fun makeUsUnitsVisible() {
        isMetricsSelected = false
        binding.lnrUSUnits.visibility = View.VISIBLE
        binding.itHeight.visibility = View.INVISIBLE

        binding.itWeight.hint = "Weight (In LB)"

        binding.etFeet.text?.clear()
        binding.etInch.text?.clear()
        clearBMIResult()
    }

    private fun makeMetricUnitsVisible() {
        isMetricsSelected = true
        binding.lnrUSUnits.visibility = View.GONE
        binding.itHeight.visibility = View.VISIBLE

        binding.itWeight.hint = "Weight (In KG)"

        binding.etHeight.text?.clear()
        clearBMIResult()
    }

    private fun clearBMIResult() {
        binding.tvBMIDescription.text = ""
        binding.tvBMIStatus.text = ""
        binding.tvBMIValue.text = ""
    }

    private fun calculateBMI() {
        if (isMetricsSelected) {
            if (validateMetricUnits()) {
                val valWeight: Float = binding.etWeight.text.toString().toFloat()
                val valHeight: Float = binding.etHeight.text.toString().toFloat() / 100
                val bmi: Float = valWeight / (valHeight * valHeight)

                displayBmiResult(bmi)
            } else {
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (validateUsUnits()) {
                val valWeight: Float = binding.etWeight.text.toString().toFloat()
                val valFeet: Float = binding.etFeet.text.toString().toFloat()
                val valInch: Float = binding.etInch.text.toString().toFloat()

                val valHeight: Float = valInch + valFeet * 12

                val bmi: Float = 703 * (valWeight / (valHeight * valHeight))

                displayBmiResult(bmi)
            } else {
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun displayBmiResult(bmi: Float) {
        val bmiStatus: String
        val bmiDescription: String

        when (bmi) {
            in 0.0..15.0 -> {
                bmiStatus = "Very severely underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
            in 15.0..16.0 -> {
                bmiStatus = "Severely underweight"
                bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
            }
            in 16.0..18.5 -> {
                bmiStatus = "Underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
            in 18.5..25.0 -> {
                bmiStatus = "Normal"
                bmiDescription = "Congratulations! You are in a good shape!"
            }
            in 25.0..30.0 -> {
                bmiStatus = "Overweight"
                bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            in 30.0..35.0 -> {
                bmiStatus = "Obese Class | (Moderately obese)"
                bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            in 35.0..40.0 -> {
                bmiStatus = "Obese Class || (Severely obese)"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
            else -> {
                bmiStatus = "Obese Class ||| (Very Severely obese)"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
        binding.tvBMIValue.text = "$bmiValue"
        binding.tvBMIStatus.text = bmiStatus
        binding.tvBMIDescription.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = false

        if (binding.etWeight.text.toString().isNotEmpty()) {
            isValid = true
        } else if (binding.etHeight.text.toString().isNotEmpty()) {
            isValid = true
        }

        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = false

        when {
            binding.etWeight.text.toString().isNotEmpty() -> isValid = true
            binding.etFeet.text.toString().isNotEmpty() -> isValid = true
            binding.etInch.text.toString().isNotEmpty() -> isValid = true
        }

        return isValid
    }

    private fun initActionBar() {
        setSupportActionBar(binding.toolbarBMI)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding.toolbarBMI.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}