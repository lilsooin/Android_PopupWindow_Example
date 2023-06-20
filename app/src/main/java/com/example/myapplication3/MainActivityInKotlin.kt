package com.example.myapplication3

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class MainActivityInKotlin : AppCompatActivity() {

    private val mChangeTexts: List<String> =
        listOf("Popup window", "The popup window", "current activity")
    private val mTextColors: List<String> = listOf("#cbff41", "#ff0000", "#cbff41")
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tooltipButton = findViewById<ImageButton>(R.id.tooltip_button)

        val inflater = baseContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val tooltipView = inflater.inflate(R.layout.tooltip_layout, null)
        val tooltipText = tooltipView.findViewById<TextView>(R.id.tooltip_text)

        // change text colors
        val totalText =
            "Popup window can be used to display an arbitrary view.\nThe popup window is a floating container that appears on top of the current activity. "
        val spannableString = SpannableString(totalText)
        changeTextColors(spannableString, totalText, mChangeTexts, mTextColors)
        tooltipText.text = spannableString

        val width = ViewGroup.LayoutParams.WRAP_CONTENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        val focusable = true

        val tooltip = PopupWindow(tooltipView, width, height, focusable)
        tooltipButton.setOnClickListener { view ->
            if (tooltip.isShowing) {
                tooltipButton.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.default_tooltip_button,
                        null
                    )
                )
            } else {
                tooltipButton.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.pressed_tooltip_button,
                        null
                    )
                )
                tooltip.showAsDropDown(view)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (tooltip.isShowing) {
                        tooltip.dismiss()
                    }
                }, 5000) // close in 5s
            }
        }

        tooltip.setOnDismissListener {
            tooltipButton.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.default_tooltip_button,
                    null
                )
            )
        }
    }

    private fun changeTextColors(
        spannableString: SpannableString,
        totalText: String,
        changeTexts: List<String>,
        textColors: List<String>
    ) {
        if (changeTexts.size != textColors.size) {
            throw RuntimeException("changeTexts size and textColors size should be same!")
        }
        for (i in changeTexts.indices) {
            val start = totalText.indexOf(changeTexts[i])
            val end = start + changeTexts[i].length
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor(textColors[i])),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}