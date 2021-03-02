package com.example.foody.activities;

import com.example.foody.R;
import com.example.foody.Utils;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import com.example.foody.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
>>>>>>> parent of c69de9a (Cleaned the code, added delete function, clean localdb function and more)
=======
import com.example.foody.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
>>>>>>> parent of c69de9a (Cleaned the code, added delete function, clean localdb function and more)
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;


public class LoginPageActivity extends AppCompatActivity {

    ImageView backgroundImageView;
    EditText emailInput;
    EditText passwordInput;
    Button loginBtn;
    Button registerBtn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        this.setTitle("Login");

        backgroundImageView = findViewById(R.id.login_activity_background_image_view);
        emailInput = findViewById(R.id.login_activity_email_edit_text);
        passwordInput = findViewById(R.id.login_activity_password_edit_text);
        registerBtn = findViewById(R.id.login_activity_register_btn);
<<<<<<< HEAD

<<<<<<< HEAD

=======
>>>>>>> parent of c69de9a (Cleaned the code, added delete function, clean localdb function and more)
=======

>>>>>>> parent of c69de9a (Cleaned the code, added delete function, clean localdb function and more)
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterPage();
            }
        });

        loginBtn = findViewById(R.id.login_activity_login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        Utils.animateBackground(backgroundImageView);
    }

    private void loginUser(){
        if (!emailInput.getText().toString().isEmpty() && !passwordInput.getText().toString().isEmpty()){
            if (firebaseAuth.getCurrentUser() == null){
                firebaseAuth.signInWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginPageActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
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
                firebaseAuth.signOut();
                Toast.makeText(this, "Previous user signed out, please try again now", Toast.LENGTH_SHORT).show();
            }
<<<<<<< HEAD
=======

            firebaseAuth.signInWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginPageActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                    setUserAppData(emailInput.getText().toString());
                    startActivity(new Intent(LoginPageActivity.this, HomeActivity.class));
                    LoginPageActivity.this.finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginPageActivity.this, "Failed to login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
>>>>>>> parent of c69de9a (Cleaned the code, added delete function, clean localdb function and more)
        }
        else {
            Toast.makeText(this, "Please fill both data fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void toRegisterPage(){
        Intent intent = new Intent(this, RegisterPageActivity.class);
        startActivity(intent);
    }

<<<<<<< HEAD
<<<<<<< HEAD
}
=======
=======
>>>>>>> parent of c69de9a (Cleaned the code, added delete function, clean localdb function and more)
    private void setUserAppData(final String email){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("userProfileData").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    User.getInstance().userUsername = (String) task.getResult().get("username");
                    User.getInstance().profileImageUrl = (String) task.getResult().get("profileImageUrl");
                    User.getInstance().userInfo = (String) task.getResult().get("info");
                    User.getInstance().userEmail = email;
                    User.getInstance().userId = firebaseAuth.getUid();
                }
            }
        });
    }

}

>>>>>>> parent of c69de9a (Cleaned the code, added delete function, clean localdb function and more)
