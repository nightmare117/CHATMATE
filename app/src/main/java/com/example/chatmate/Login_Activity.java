package com.example.chatmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

    private Toolbar toolbarLogin;
    private Button loginButton;
    private EditText email,pass;
    private TextView textSignUp;
    private FirebaseAuth mAuth;
    private ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        toolbarLogin=(Toolbar)findViewById(R.id.loginToolBar);
        email=(EditText)findViewById(R.id.LoginEmail);
        pass=(EditText)findViewById(R.id.loginPass);
        progressBarLogin=(ProgressBar)findViewById(R.id.progressBarLogin);
        progressBarLogin.setVisibility(View.INVISIBLE);

        loginButton=(Button)findViewById(R.id.buttonLogin);

        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbarLogin);
        getSupportActionBar().setTitle("ChatMate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        textSignUp=(TextView)findViewById(R.id.loginRegText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailLogin = email.getEditableText().toString();
                String passLogin=pass.getEditableText().toString();

                if(!TextUtils.isEmpty(emailLogin)&&!TextUtils.isEmpty(passLogin)){
                    progressBarLogin.setVisibility(View.VISIBLE);
                    loginUser(emailLogin,passLogin);
                }
                else{
                    Toast.makeText(Login_Activity.this,"Something is missing",Toast.LENGTH_LONG).show();
                }

            }
        });

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login_Activity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void loginUser(String emailLogin, String passLogin) {
        mAuth.signInWithEmailAndPassword(emailLogin,passLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBarLogin.setVisibility(View.GONE);
                    Intent intent=new Intent(Login_Activity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Login_Activity.this,"Login Unsuccessful",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}