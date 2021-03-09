package com.example.foody.activities;

import com.example.foody.R;
import com.example.foody.Utils;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import de.hdodenhof.circleimageview.CircleImageView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.example.foody.model.ModelFirebase;

import androidx.annotation.Nullable;


public class RegisterPageActivity extends AppCompatActivity {

    ImageView backgroundImageView;
    EditText usernameInput;
    EditText passwordInput;
    EditText emailInput;
    Button registerB;
    CircleImageView profileImageView;
    ImageButton closeB;
    ProgressBar progressBar;
    Uri profileImageUri = null;


    //-----------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        usernameInput = findViewById(R.id.register_activity_username_edit_text);
        passwordInput = findViewById(R.id.register_activity_password_edit_text);
        emailInput = findViewById(R.id.register_activity_email_edit_text);
        profileImageView = findViewById(R.id.register_activity_profile_image_view);
        backgroundImageView = findViewById(R.id.register_activity_background_image_view);

        Utils.animateBackground(backgroundImageView, 3000);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.chooseImageFromGallery(RegisterPageActivity.this);
            }
        });

        progressBar = findViewById(R.id.register_activity_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        registerB = findViewById(R.id.register_activity_register_btn);


        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                ModelFirebase.registerUserAccount(usernameInput.getText().toString(),
                        passwordInput.getText().toString(), emailInput.getText().toString(), profileImageUri, new ModelFirebase.Listener<Boolean>() {

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.INVISIBLE);
                        RegisterPageActivity.this.finish();
                    }

                    @Override
                    public void onFail() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

        closeB = findViewById(R.id.register_activity_close_btn);
        closeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterPageActivity.this.finish();
            }
        });

    }


    //-----------------------------------------------------------------------------------------------------


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null && data != null){
            profileImageUri = data.getData();
            profileImageView.setImageURI(profileImageUri);
        }
        else {
            Toast.makeText(this, "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }
}