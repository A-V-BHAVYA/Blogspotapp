package com.example.blogspot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    Button btnLogOut;
    Button createPlot;
    Button viewplot;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    String usertype;
    String child;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        final DatabaseReference mref=FirebaseDatabase.getInstance().getReference().child("");
        firebaseAuth = FirebaseAuth.getInstance();
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Intent intent =getIntent();
        final LinearLayout ll=findViewById(R.id.myll);
        createPlot = findViewById(R.id.btncreateplot);
        viewplot = findViewById(R.id.btnviewplot);
        child=intent.getStringExtra("email");
        System.out.println("user child:"+child);

        createPlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent I = new Intent(UserActivity.this, PostActivity.class);
                I.putExtra("email",child);
                startActivity(I);

            }
        });
        viewplot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent I = new Intent(UserActivity.this, ViewActivity.class);
                I.putExtra("email",child);
                startActivity(I);

            }
        });

        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(UserActivity.this, ActivityLogin.class);
                startActivity(I);

            }
        });



    }
}