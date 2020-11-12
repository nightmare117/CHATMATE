package com.example.chatmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference userDatabase;
    private FirebaseUser currentUser;

    private CircleImageView displayImage;
    private TextView textStatus,textName;

    private Button buttonChangeStatus,buttonChangeImage;
    private static final int GINT =1;

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textStatus=(TextView)findViewById(R.id.profileStatus);
        textName=(TextView)findViewById(R.id.profileName);

        buttonChangeStatus=(Button)findViewById(R.id.profileChangeStatus);
        buttonChangeImage=(Button)findViewById(R.id.profileChangeImage);

        displayImage=(CircleImageView)findViewById(R.id.profile_image);

        currentUser= FirebaseAuth.getInstance().getCurrentUser();

        mStorageRef= FirebaseStorage.getInstance().getReference();

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

        buttonChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent gIntent=new Intent();
                gIntent.setType("image/*");
                gIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gIntent,"SELECT IMAGE"),GINT);*/

                // start picker to get image for cropping and then use the image in cropping activity
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1)
                        .start(ProfileActivity.this);
            }
        });

        buttonChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status_extra=textStatus.getText().toString();
                Intent intent=new Intent(ProfileActivity.this,StatusActivity.class);
                intent.putExtra("status_key",status_extra);
                startActivity(intent);
            }
        });


    }

    //image uploading process
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String uid=currentUser.getUid();
                StorageReference filepath= mStorageRef.child("profile_images").child(uid+".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this,"Upload Successful",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ProfileActivity.this,"Something is Wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}