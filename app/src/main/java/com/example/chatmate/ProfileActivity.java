package com.example.chatmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference userDatabase;
    private FirebaseUser currentUser;

    private CircleImageView displayImage;
    private TextView textStatus,textName;

    private Button buttonChangeStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textStatus=(TextView)findViewById(R.id.profileStatus);
        textName=(TextView)findViewById(R.id.profileName);

        buttonChangeStatus=(Button)findViewById(R.id.profileChangeStatus);

        displayImage=(CircleImageView)findViewById(R.id.profile_image);

        currentUser= FirebaseAuth.getInstance().getCurrentUser();

        String current_uid= currentUser.getUid();
        userDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mname = snapshot.child("name").getValue().toString();
                String mstatus = snapshot.child("status").getValue().toString();
                String mimage = snapshot.child("image").getValue().toString();
                String mthumb_image = snapshot.child("thumb_image").getValue().toString();

                textName.setText(mname);
                textStatus.setText(mstatus);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this,StatusActivity.class);
                startActivity(intent);
            }
        });


    }
}