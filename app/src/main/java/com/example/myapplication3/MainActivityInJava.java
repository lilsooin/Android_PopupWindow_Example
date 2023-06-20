package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityInJava extends AppCompatActivity {

    private static final String TAG = "MainActivityInJava";

    private List<String> mChangeTexts = new ArrayList<>(Arrays.asList("Popup window", "The popup window", "current activity"));
    private List<String> mTextColors = new ArrayList<>(Arrays.asList("#cbff41", "#ff0000", "#cbff41"));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton tooltipButton = findViewById(R.id.tooltip_button);

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View tooltipView = inflater.inflate(R.layout.tooltip_layout, null);
        TextView tooltipText = tooltipView.findViewById(R.id.tooltip_text);

        // change text colors
        String totalText = "Popup window can be used to display an arbitrary view.\nThe popup window is a floating container that appears on top of the current activity. ";
        SpannableString spannableString = new SpannableString(totalText);
        changeTextColors(spannableString, totalText, mChangeTexts, mTextColors);
        tooltipText.setText(spannableString);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        PopupWindow tooltip = new PopupWindow(tooltipView, width, height, focusable);
        tooltipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tooltip.isShowing()) {
                    tooltipButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.default_tooltip_button, null));
                } else {
                    tooltipButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pressed_tooltip_button, null));
                    tooltip.showAsDropDown(view);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tooltip.isShowing()) {
                                tooltip.dismiss();
                            }
                        }
                    }, 5000);// close in 5s
                }
            }
        });

        tooltip.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDismiss() {
                tooltipButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.default_tooltip_button, null));
            }
        });


    }


    public void changeTextColors(SpannableString spannableString, String totalText, List<String> changeTexts, List<String> textColors) {
        if (changeTexts.size() != textColors.size()) {
            throw new RuntimeException("changeTexts size and textColors size should be same!");
        }

        for (int i = 0; i < changeTexts.size(); ++i) {
            int start = totalText.indexOf(changeTexts.get(i));
            int end = start + changeTexts.get(i).length();
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(textColors.get(i))), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

}
