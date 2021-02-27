package com.example.foody.activities;
import com.example.foody.R;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginPageActivity extends AppCompatActivity {

    ImageView backgroundImageView;
    EditText emailInput;
    EditText passwordInput;
    Button loginBtn;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        this.setTitle("Login");

        backgroundImageView = findViewById(R.id.login_activity_background_image_view);
        emailInput = findViewById(R.id.login_activity_email_edit_text);
        passwordInput = findViewById(R.id.login_activity_password_edit_text);
        loginBtn = findViewById(R.id.login_activity_login_btn);
        registerBtn = findViewById(R.id.login_activity_register_btn);

        animateBackground();

    }

    private void animateBackground(){
        backgroundImageView.animate().scaleX((float) 1.5).scaleY((float) 1.5).setDuration(20000).start();
    }
}