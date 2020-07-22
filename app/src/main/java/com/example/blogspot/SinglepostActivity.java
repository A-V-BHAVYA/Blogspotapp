package com.example.blogspot;


import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SinglepostActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabaseReference;
    int count = 0;
    String child,address,title,author;
    String imageurl;
    String total;
    private FirebaseAuth.AuthStateListener authStateListener;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    public static boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carditems);
        Intent intent = getIntent();
        child = getIntent().getStringExtra("child");
        address=intent.getStringExtra("address");
        title=intent.getStringExtra("title");
        author=intent.getStringExtra("by");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot vinesnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : vinesnapshot.child("Blogzone").getChildren())
                            if(dataSnapshot1.child("title").getValue().toString().equals(title)) {
                                imageurl = dataSnapshot1.child("imageUrl").getValue().toString();
                                String descrip = dataSnapshot1.child("desc").getValue().toString();
                                String title = dataSnapshot1.child("title").getValue().toString();
                                display(imageurl, descrip, title,author);
                                flag = true;
                                break;
                            }


                    if (flag)
                        break;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }

        });

    }
    public void display(String url,String desc,String titl,String name){
        //StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("All_Image_Uploads/plot/" );
        System.out.println(url+"   "+titl+"       "+desc);


       ImageView imageView = findViewById(R.id.imageview);
       TextView title=findViewById(R.id.post_title_txtview);
       TextView descinfo=findViewById(R.id.post_desc_txtview);
       TextView nam=findViewById(R.id.post_user);


        Glide.with(getApplicationContext()).load(url).into(imageView);

        title.setText(titl);
        descinfo.setText(desc);
        nam.setText(name);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent I = new Intent(SinglepostActivity.this, ViewActivity.class);
        I.putExtra("email",child);
        finish();
        startActivity(I);


    }
}
