package com.example.registerpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    //properties
    private Button signUpButton, loginBtn;
    private ImageView image;
    private TextView logoText;
    private TextInputLayout username, password1, password2, gmail;
    private FirebaseAuth dbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        dbAuth = FirebaseAuth.getInstance();

        signUpButton = findViewById(R.id.buttonSignUp2);
        loginBtn = findViewById(R.id.buttonLogIn2);
        image = findViewById(R.id.gifImageView2);
        logoText = findViewById(R.id.textId2);
        username = findViewById(R.id.username2);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        gmail = findViewById(R.id.email);

    }

    //move to sign in screen
    public void signUp(View view) {
        Intent i = new Intent(this,MainActivity.class);

        Pair[] pairs = new Pair[6];

        pairs[0] = new Pair<View, String>(image, "logo_image");
        pairs[1] = new Pair<View, String>(signUpButton, "sign_up_tran");
        pairs[2] = new Pair<View, String>(loginBtn, "button_tran");
        pairs[3] = new Pair<View, String>(username, "username_tran");
        pairs[4] = new Pair<View, String>(password1, "password_tran");
        pairs[5] = new Pair<View, String>(password1, "password_tran");


        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this, pairs);
        startActivity(i, options.toBundle());
    }

    //btn to sign up a new account
    public void btnSignup(View view)
    {
        integrityCheck();
    }

    //check if all field in edit text are ok
    private void integrityCheck()
    {
        String usernameText = username.getEditText().getText().toString().trim();
        String passwordText1 = password1.getEditText().getText().toString().trim();
        String passwordText2 = password2.getEditText().getText().toString().trim();
        String gmailText = gmail.getEditText().getText().toString().trim();

        if(usernameText.isEmpty())
        {
            username.setError("Username is required!");
            username.requestFocus();
            return;
        }
        else
            username.setError(null);


        if(passwordText1.isEmpty())
        {
            password1.setError("Password is required!");
            password1.requestFocus();
            return;
        }
        else
            password1.setError(null);

        if(passwordText2.isEmpty())
        {
            password2.setError("Password is required!");
            password2.requestFocus();
            return;
        }
        else
            password2.setError(null);

        if(passwordText1.length() > 15)
        {
            password1.setError("Max length is 15!");
            password1.requestFocus();
            return;
        }
        else
            password1.setError(null);

        if(passwordText2.length() > 15)
        {
            password2.setError("Max length is 15!");
            password2.requestFocus();
            return;
        }
        else
            password2.setError(null);

        if(!passwordText2.equals(passwordText1))
        {
            password2.setError("Please enter same password!");
            password2.requestFocus();
            return;
        }

        if(gmailText.isEmpty())
        {
            gmail.setError("Gmail is required!");
            gmail.requestFocus();
            return;
        }
        else
            gmail.setError(null);

        if(!Patterns.EMAIL_ADDRESS.matcher(gmailText).matches())
        {
            gmail.setError("Please provide valid gmail!");
            gmail.requestFocus();
        }
        else
            gmail.setError(null);

        dbAuth.createUserWithEmailAndPassword(gmailText, passwordText1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    CreateUser user = new CreateUser(usernameText, passwordText1, gmailText);

                    FirebaseDatabase.getInstance("https://register-page-a3cc0-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(SignUpActivity.this, "The user created successfully!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}