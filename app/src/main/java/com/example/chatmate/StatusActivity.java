package com.example.chatmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private Toolbar sToolbar;
    private EditText editText1;
    private Button buttonChange;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        sToolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbarStatus);
        setSupportActionBar(sToolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText1=(EditText)findViewById(R.id.statusChange);
        buttonChange=(Button)findViewById(R.id.statusSaveButton);
        progressBar=(ProgressBar)findViewById(R.id.progressBarStatus);
        progressBar.setVisibility(View.INVISIBLE);

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String status= editText1.getEditableText().toString();
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                databaseReference.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful()){
                            Toast.makeText(StatusActivity.this, "Successfully Updated Your Status!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(StatusActivity.this,"Error Occurred!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }
}