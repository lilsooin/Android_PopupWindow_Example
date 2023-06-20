package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class MainActivityInJava extends AppCompatActivity {

    private static final String TAG = "MainActivityInJava";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton tooltipButton = findViewById(R.id.tooltip_button);

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View tooltipView = inflater.inflate(R.layout.tooltip_layout, null);

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
}
