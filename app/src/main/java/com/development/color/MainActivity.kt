package com.development.color

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

@SuppressLint("UseSwitchCompatOrMaterialCode")
class MainActivity : AppCompatActivity() {
    private lateinit var redSeekBar: SeekBar
    private lateinit var greenSeekBar: SeekBar
    private lateinit var blueSeekBar: SeekBar
    private lateinit var colorBox: View
    private lateinit var greenSwitch: Switch
    private lateinit var redSwitch: Switch
    private lateinit var blueSwitch: Switch
    private lateinit var redEditText: EditText
    private lateinit var greenEditText: EditText
    private lateinit var blueEditText: EditText
    private lateinit var resetButton: Button


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        redSeekBar = findViewById<SeekBar>(R.id.redSeekBar)
        greenSeekBar = findViewById<SeekBar>(R.id.greenSeekBar)
        blueSeekBar = findViewById<SeekBar>(R.id.blueSeekBar)
        colorBox = findViewById<View>(R.id.colorBox)
        resetButton = findViewById<Button>(R.id.resetButton)
        redSwitch = findViewById<Switch>(R.id.redSwitch)
        blueSwitch = findViewById<Switch>(R.id.blueSwitch)
        greenSwitch = findViewById<Switch>(R.id.greenSwitch)
        redEditText = findViewById<EditText>(R.id.redEditTextNumberDecimal)
        greenEditText = findViewById<EditText>(R.id.greenEditTextNumberDecimal)
        blueEditText = findViewById<EditText>(R.id.blueEditTextNumberDecimal)


        redSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateColorBox(
                    colorBox,
                    seekBar.progress,
                    greenSeekBar.progress,
                    blueSeekBar.progress
                )
                val progressValue = (progress / 100.0).toString()
                redEditText.setText(progressValue)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        greenSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateColorBox(
                    colorBox,
                    redSeekBar.progress,
                    seekBar.progress,
                    blueSeekBar.progress
                )
                val progressValue = (progress / 100.0).toString()
                greenEditText.setText(progressValue)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        blueSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateColorBox(
                    colorBox,
                    redSeekBar.progress,
                    greenSeekBar.progress,
                    seekBar.progress
                )
                val progressValue = (progress / 100.0).toString()
                blueEditText.setText(progressValue)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        redSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                redSeekBar.progressDrawable.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(this, R.color.red), PorterDuff.Mode.MULTIPLY)
                redSwitch.thumbTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.red))
            } else{
                redSeekBar.progressDrawable.colorFilter = null
                redSwitch.thumbTintList = null
            }
            redSeekBar.isEnabled = isChecked
            redEditText.isEnabled = isChecked
        }

        greenSwitch.setOnCheckedChangeListener { _, isChecked ->
            greenSeekBar.isEnabled = isChecked
            greenEditText.isEnabled = isChecked
        }

        blueSwitch.setOnCheckedChangeListener { _, isChecked ->
            blueSeekBar.isEnabled = isChecked
            blueEditText.isEnabled = isChecked
        }

        redEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString().toFloatOrNull() ?: 0f
                val progress = (input.coerceIn(0f, 1f) * 100).toInt()
                redSeekBar.progress = progress
                updateColorBox(colorBox, redSeekBar.progress, greenSeekBar.progress, blueSeekBar.progress )
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        greenEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString().toFloatOrNull() ?: 0f
                val progress = (input.coerceIn(0f, 1f) * 100).toInt()
                greenSeekBar.progress = progress
                updateColorBox(colorBox, redSeekBar.progress, greenSeekBar.progress, blueSeekBar.progress )
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        blueEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString().toFloatOrNull() ?: 0f
                val progress = (input.coerceIn(0f, 1f) * 100).toInt()
                blueSeekBar.progress = progress
                updateColorBox(colorBox, redSeekBar.progress, greenSeekBar.progress, blueSeekBar.progress )
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        resetButton.setOnClickListener {
            redSeekBar.progress = 0
            greenSeekBar.progress = 0
            blueSeekBar.progress = 0
            redEditText.setText("0")
            blueEditText.setText("0")
            greenEditText.setText("0")
            updateColorBox(colorBox, 0, 0, 0)
        }

    }

    fun updateColorBox(colorBox: View, redProgress: Int, greenProgress2: Int, blueProgress: Int) {
        val color = Color.rgb(redProgress, greenProgress2, blueProgress, )
        colorBox.setBackgroundColor(color)
    }
}