package com.example.registerpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

public class MainActivity extends AppCompatActivity {
    //properties
    private Button signUpButton, loginBtn;
    private ImageView image;
    private TextView logoText;
    private TextInputLayout username, password;
    private FirebaseAuth dbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        dbAuth = FirebaseAuth.getInstance();

        signUpButton = findViewById(R.id.buttonSignUp);
        loginBtn = findViewById(R.id.buttonLogIn);
        image = findViewById(R.id.gifImageView);
        logoText = findViewById(R.id.textId);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    //move to sign up page with animation
    public void signup(View view) {
        Intent i = new Intent(this,SignUpActivity.class);

        Pair[] pairs = new Pair[5];

        pairs[0] = new Pair<View, String>(image, "logo_image");
        pairs[1] = new Pair<View, String>(signUpButton, "sign_up_tran");
        pairs[2] = new Pair<View, String>(loginBtn, "button_tran");
        pairs[3] = new Pair<View, String>(username, "username_tran");
        pairs[4] = new Pair<View, String>(password, "password_tran");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
        startActivity(i, options.toBundle());
    }

    //log in button to join the game flappy bird
    public void btnLogIn(View view) {
        userLogin();
    }

    //check in data base if exist this user
    private void userLogin() {
        //check if all field in edit text are ok
        String usernameText = username.getEditText().getText().toString().trim();
        String passwordText = password.getEditText().getText().toString().trim();

        if(usernameText.isEmpty())
        {
            username.setError("Username is required!");
            username.requestFocus();
            return;
        }
        else
            username.setError(null);

        if(passwordText.isEmpty())
        {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        else
            password.setError(null);

        if(passwordText.length() > 15)
        {
            password.setError("Max length is 15!");
            password.requestFocus();
        }
        else
            password.setError(null);

        dbAuth.signInWithEmailAndPassword(usernameText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(MainActivity.this, LogInFinal.class));
                }
                else
                    Toast.makeText(MainActivity.this, "Does not exit user with these details", Toast.LENGTH_LONG).show();
            }});


    }


}