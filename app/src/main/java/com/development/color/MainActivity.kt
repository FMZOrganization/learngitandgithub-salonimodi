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
            setColorSwitchListener(redSwitch, redSeekBar, redEditText, R.color.red,isChecked )
        }

        greenSwitch.setOnCheckedChangeListener { _, isChecked ->
            setColorSwitchListener(greenSwitch, greenSeekBar, greenEditText, R.color.green, isChecked )
        }

        blueSwitch.setOnCheckedChangeListener { _, isChecked ->
            setColorSwitchListener(blueSwitch, blueSeekBar, blueEditText, R.color.blue,isChecked )
        }

        redEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setupEditText(s, redSeekBar, colorBox)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        greenEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setupEditText(s, greenSeekBar, colorBox)
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
            redSwitch.isChecked = true
            greenSwitch.isChecked = true
            blueSwitch.isChecked = true
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

    @RequiresApi(Build.VERSION_CODES.M)
    fun setColorSwitchListener(
        switch: Switch,
        seekBar: SeekBar,
        editText: EditText,
        color: Int,
        isChecked: Boolean
    ) {
            if (isChecked) {
                seekBar.progressDrawable.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(this, color), PorterDuff.Mode.MULTIPLY
                )
                switch.thumbTintList = ColorStateList.valueOf(ContextCompat.getColor(this, color))
            } else {
                seekBar.progressDrawable.colorFilter = null
                switch.thumbTintList = null
            }
            seekBar.isEnabled = isChecked
            editText.isEnabled = isChecked
    }



}