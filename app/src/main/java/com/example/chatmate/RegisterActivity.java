package com.example.chatmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
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

public class RegisterActivity extends AppCompatActivity {

    private Button createAccount;
    private EditText name,email1,password1;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private TextView textLogin;

    private ProgressBar progressBar;

    //Progress Dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccount=(Button)findViewById(R.id.regCreateAccount);
        name=(EditText)findViewById(R.id.regUserName);
        email1=(EditText)findViewById(R.id.regUserEmail);
        password1=(EditText)findViewById(R.id.regUserPassword);
        textLogin=(TextView)findViewById(R.id.textView4);
        progressBar=(ProgressBar)findViewById(R.id.progressBarReg);

        progressBar.setVisibility(View.INVISIBLE);

        toolbar=findViewById(R.id.regtoolbar);

        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ChatMate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=name.getEditableText().toString();
                String useremail=email1.getEditableText().toString();
                String userpassword = password1.getEditableText().toString();
                if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(useremail)&&!TextUtils.isEmpty(userpassword)) {
                    progressBar.setVisibility(View.VISIBLE);
                    userRegisterMethod(username, useremail, userpassword);
                }
                else {
                    Toast.makeText(RegisterActivity.this,"Something is missing",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void userRegisterMethod(String username, String useremail, String userpassword) {

        mAuth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Intent mainIntent=new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(RegisterActivity.this,"Registration Unsuccessful!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}