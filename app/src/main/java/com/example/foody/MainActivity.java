package com.example.foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.foody.activities.LoginPageActivity;


public class MainActivity extends AppCompatActivity {

    ImageView backgroundImageView;
    Button exploreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundImageView = findViewById(R.id.main_background_image_view);
        exploreBtn = findViewById(R.id.main_explore_button);
        animateBackground();

        exploreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLoginPage();
            }
        });
    }

    private void animateBackground(){
        backgroundImageView.animate().scaleX((float) 1.5).scaleY((float) 1.5).setDuration(20000).start();
    }

    private void toLoginPage(){
        Intent intent = new Intent(this, LoginPageActivity.class);
        startActivity(intent);
    }

}