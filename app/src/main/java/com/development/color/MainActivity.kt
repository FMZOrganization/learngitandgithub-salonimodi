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
import kotlin.math.roundToInt

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
            if(isChecked){
                greenSeekBar.progressDrawable.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(this, R.color.green), PorterDuff.Mode.MULTIPLY)
                greenSwitch.thumbTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.green))
            } else{
                greenSeekBar.progressDrawable.colorFilter = null
                greenSwitch.thumbTintList = null
            }
            greenSeekBar.isEnabled = isChecked
            greenEditText.isEnabled = isChecked
        }

        blueSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                blueSeekBar.progressDrawable.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(this, R.color.blue), PorterDuff.Mode.MULTIPLY)
                blueSwitch.thumbTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.blue))
            } else{
                blueSeekBar.progressDrawable.colorFilter = null
                blueSwitch.thumbTintList = null
            }
            blueSeekBar.isEnabled = isChecked
            blueEditText.isEnabled = isChecked
        }

        redEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setupEditText(s, blueSeekBar, colorBox)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        greenEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setupEditText(s, blueSeekBar, colorBox)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        blueEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setupEditText(s, blueSeekBar, colorBox)
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
            redSwitch.isEnabled = true
            greenSwitch.isEnabled = true
            blueSwitch.isEnabled = true
            updateColorBox(colorBox, 0, 0, 0)
        }

    }

    fun updateColorBox(colorBox: View, redProgress: Int, greenProgress: Int, blueProgress: Int) {
        val red = (redProgress / 100.0f).coerceIn(0.0f, 1.0f)
        val green = (greenProgress / 100.0f).coerceIn(0.0f, 1.0f)
        val blue = (blueProgress / 100.0f).coerceIn(0.0f, 1.0f)

        val color = Color.rgb((red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt())
        colorBox.setBackgroundColor(color)
    }

    fun setupEditText(input: CharSequence?, seekBar: SeekBar, colorBox: View) {
        val value = input?.toString()?.toFloatOrNull() ?: 0f
        val progress = (value.coerceIn(0f, 1f) * 100 + 0.5f).toInt().coerceAtLeast(1)
        seekBar.progress = progress
        updateColorBox(colorBox, redSeekBar.progress, greenSeekBar.progress, blueSeekBar.progress)
    }


}