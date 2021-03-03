package com.example.foody.activities;

import com.example.foody.R;
import com.example.foody.Utils;
import com.example.foody.model.Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;


public class LoginPageActivity extends AppCompatActivity {

    ImageView backgroundImageView;
    EditText emailInput;
    EditText passwordInput;
    Button loginB;
    Button registerB;
    FirebaseAuth firebaseAuth;

    //-----------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        firebaseAuth = FirebaseAuth.getInstance();

        this.setTitle("Login");

        backgroundImageView = findViewById(R.id.login_activity_background_image_view);
        emailInput = findViewById(R.id.login_activity_email_edit_text);
        passwordInput = findViewById(R.id.login_activity_password_edit_text);
        registerB = findViewById(R.id.login_activity_register_btn);

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterPage();
            }
        });

        loginB = findViewById(R.id.login_activity_login_btn);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 loginUser();
            }
        });
        Utils.animateBackground(backgroundImageView);

    }

    //-----------------------------------------------------------------------------------------------------


    private void loginUser(){

        if (!emailInput.getText().toString().isEmpty() && !passwordInput.getText().toString().isEmpty()){

            if (firebaseAuth.getCurrentUser() != null) {
                firebaseAuth.signOut();
            }

            firebaseAuth.signInWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    Toast.makeText(LoginPageActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                    Model.instance.setUserAppData(emailInput.getText().toString());
                    startActivity(new Intent(LoginPageActivity.this, HomeActivity.class));
                    LoginPageActivity.this.finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginPageActivity.this, "Failed to login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(this, "Please fill both data fields", Toast.LENGTH_SHORT).show();
        }

    }

    private void toRegisterPage(){
        Intent intent = new Intent(this, RegisterPageActivity.class);
        startActivity(intent);
    }


}

