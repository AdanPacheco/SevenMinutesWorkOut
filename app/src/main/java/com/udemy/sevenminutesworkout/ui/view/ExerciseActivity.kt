package com.udemy.sevenminutesworkout.ui.view

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.udemy.sevenminutesworkout.R
import com.udemy.sevenminutesworkout.databinding.ActivityExerciseBinding
import com.udemy.sevenminutesworkout.databinding.CustomDialogBackPressedBinding
import com.udemy.sevenminutesworkout.core.Constants
import com.udemy.sevenminutesworkout.data.db.model.ExerciseModel
import com.udemy.sevenminutesworkout.ui.viewmodel.exerciseAdapter.ExerciseStatusAdapter
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityExerciseBinding

    private var countDownTimer: CountDownTimer? = null
    private var restProgress = 0

    private var countDownExerciseTimer: CountDownTimer? = null
    private var restExerciseProgress = 0

    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private var currentExercisePosition = 0

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private lateinit var adapter: ExerciseStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        exerciseList = Constants.defaultExerciseList()
        tts = TextToSpeech(this, this)
        initActionBar()
        setupPlayer()
        setupRecyclerViewStatusExercise()
    }

    private fun setupRecyclerViewStatusExercise() {
        binding.rvExerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = ExerciseStatusAdapter(exerciseList)
        binding.rvExerciseStatus.adapter = adapter
    }

    private fun setupPlayer() {
        try {
            val soundURI = Uri.parse("android.resource://com.udemy.sevenminutesworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupRestProgressBar() {
        val ex = exerciseList[currentExercisePosition]
        binding.frmProgressBar.visibility = View.VISIBLE
        binding.frmProgressBarEx.visibility = View.INVISIBLE
        binding.ivExercise.visibility = View.INVISIBLE
        binding.lnrExerciseDesc.visibility = View.VISIBLE
        binding.tvExerciseDesc.text = ex.getName()
        restProgress = 0

        ex.setIsSelected(true)
        adapter.notifyItemChanged(currentExercisePosition)

        speakOut("Upcoming exercise ${ex.getName()}")
        startTimer()
    }

    private fun setupExerciseProgressBar() {
        val ex = exerciseList[currentExercisePosition]
        binding.frmProgressBar.visibility = View.INVISIBLE
        binding.frmProgressBarEx.visibility = View.VISIBLE
        binding.ivExercise.visibility = View.VISIBLE
        binding.lnrExerciseDesc.visibility = View.INVISIBLE



        binding.ivExercise.setImageResource(ex.getImage())
        binding.tvTitle.text = ex.getName()
        restExerciseProgress = 0

        startExerciseTimer()
    }

    private fun startExerciseTimer() {
        binding.progressBarEx.progress = restExerciseProgress
        countDownExerciseTimer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                restExerciseProgress++
                val restTime = 30 - restExerciseProgress
                binding.progressBarEx.progress = restTime
                binding.tvTimerEx.text = "$restTime"

                if (restTime == 1) {
                    player?.start()
                }
            }

            override fun onFinish() {
                if (currentExercisePosition >= exerciseList.size) {
                    startActivity(Intent(this@ExerciseActivity,FinishActivity::class.java))
                    finish()
                    return
                }

                exerciseList[currentExercisePosition-1].setIsSelected(false)
                exerciseList[currentExercisePosition-1].setIsCompleted(true)
                adapter.notifyItemChanged(currentExercisePosition-1)

                setupRestProgressBar()
            }
        }.start()
    }

    private fun startTimer() {
        binding.progressBar.progress = restProgress
        countDownTimer = object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                val restTime = 10 - restProgress
                binding.progressBar.progress = restTime
                binding.tvTimer.text = "$restTime"

                if (restTime == 2) {
                    player?.start()
                }
            }

            override fun onFinish() {
                setupExerciseProgressBar()
                currentExercisePosition++
            }
        }.start()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.toolbarExc)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarExc.setNavigationOnClickListener {
            showCustomBackPressedDialog()
        }
    }

    private fun showCustomBackPressedDialog() {
        val customDialog = Dialog(this)
        val dialogBinding = CustomDialogBackPressedBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported")
            }
        } else {
            Log.e("TTS", "Initialization failed")
        }
        setupRestProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            restProgress = 0
        }

        if (countDownExerciseTimer != null) {
            countDownExerciseTimer?.cancel()
            restExerciseProgress = 0
        }

        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }

        if (player != null) {
            player?.stop()
        }
    }


}