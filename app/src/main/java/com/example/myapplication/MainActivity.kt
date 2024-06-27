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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
        setButtonListener(targetValue)
    }

    private fun setTargetValue(targetValue: Int) {
        binding.targetValue.text = targetValue.toString()
    }

    private fun setButtonListener(targetValue: Int) {
        var buttonInstance = binding.submit
        buttonInstance.setOnClickListener {
            showResult(targetValue)
        }
    }

    private fun showResult(targetValue: Int) {
        val resultTitle = getString(R.string.result_title)

        val resultMessage = setToastMessage()

        val alertBuilder = AlertDialog.Builder(this)
            .setTitle(resultTitle)
            .setMessage(resultMessage)
            .setPositiveButton("OK") {dialog, _ -> dialog.dismiss()}
            .create()
            .show()
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
}