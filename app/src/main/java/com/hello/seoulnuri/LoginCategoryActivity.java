package com.hello.seoulnuri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class LoginCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    ToggleButton toggle_eye, toggle_wheel, toggle_ear, toggle_elder;
    ImageButton startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_category);

        toggle_eye = (ToggleButton)findViewById(R.id.button_eye);
        toggle_wheel = (ToggleButton)findViewById(R.id.button_wheel);
        toggle_ear = (ToggleButton)findViewById(R.id.button_ear);
        toggle_elder = (ToggleButton)findViewById(R.id.button_elder);
        startButton = (ImageButton)findViewById(R.id.button_start);

        toggle_eye.setOnClickListener(this);
        toggle_wheel.setOnClickListener(this);
        toggle_ear.setOnClickListener(this);
        toggle_elder.setOnClickListener(this);

        startButton.setEnabled(false);


    }

    public void startClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        checkEnable();
        switch (view.getId()){
            case R.id.button_eye:{
                if (toggle_eye.isChecked()) toggle_eye.setBackgroundResource(R.drawable.button_eye_active);
                else toggle_eye.setBackgroundResource(R.drawable.button_eye);
            }

            case R.id.button_wheel:{
                if (toggle_wheel.isChecked()) toggle_wheel.setBackgroundResource(R.drawable.button_wheel_active);
                else toggle_wheel.setBackgroundResource(R.drawable.button_wheel);
            }

            case R.id.button_ear:{
                if (toggle_ear.isChecked()) toggle_ear.setBackgroundResource(R.drawable.button_ear_active);
                else toggle_ear.setBackgroundResource(R.drawable.button_ear);
            }

            case R.id.button_elder:{
                if (toggle_elder.isChecked()) toggle_elder.setBackgroundResource(R.drawable.button_elder_active);
                else toggle_elder.setBackgroundResource(R.drawable.button_elder);
            }
        }

    }

    public void checkEnable(){
        if(toggle_eye.isChecked() || toggle_wheel.isChecked() || toggle_ear.isChecked() || toggle_elder.isChecked()){
            startButton.setImageResource(R.drawable.button_start_active);
            startButton.setEnabled(true);
        } else {
            startButton.setImageResource(R.drawable.button_start_g);
            startButton.setEnabled(false);
        }
    }
}
