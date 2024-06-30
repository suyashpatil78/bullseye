package com.example.myapplication

import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var sliderValue: Int = 0

    private var targetValue = Random.nextInt(1, 100)

    private var totalScore = 0

    private var totalRounds = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        setTargetValue(targetValue)
        setSeekbarListener()
        setButtonListener()
        setStartOverButtonListener()
    }

    private fun setTargetValue(targetValue: Int) {
        binding.targetValue.text = targetValue.toString()
    }

    private fun setButtonListener() {
        val buttonInstance = binding.submit
        buttonInstance.setOnClickListener {
            showResult()
            setTotalScore()
            setRounds()
        }
    }

    private fun showResult() {
        val resultTitle = getString(R.string.result_title)

        val resultMessage = setToastMessage()

        AlertDialog.Builder(this)
            .setTitle(resultTitle)
            .setMessage(resultMessage)
            .setPositiveButton("OK") {dialog, _ ->
                targetValue = Random.nextInt(1, 100)
                setTargetValue(targetValue)
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun setTotalScore() {
        totalScore += 100 - abs(sliderValue - targetValue)
        binding.score?.text = totalScore.toString()
    }

    private fun setRounds() {
        totalRounds += 1
        binding.round?.text = totalRounds.toString()
    }

    private fun setSeekbarListener() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }
        })
    }

    private fun setToastMessage(): String {
        return (
                "Slider value: $sliderValue \n" +
                "Target value: $targetValue \n" +
                "Difference: ${abs(sliderValue - targetValue)}\n"
                )
    }

    private fun setStartOverButtonListener() {
        val buttonInstance = binding.startOver
        val scoreTextInstance = binding.score
        val roundTextInstance = binding.round

        buttonInstance?.setOnClickListener {
            totalScore = 0
            totalRounds = 0
            scoreTextInstance?.text = totalScore.toString()
            roundTextInstance?.text = totalRounds.toString()
        }
    }
}