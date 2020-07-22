package com.example.blogspot;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabaseReference;
    private FirebaseAuth.AuthStateListener authStateListener;
    String child;
    String totfieldofficer;
    int count=0;
    public static Boolean f=false;

    List<Button> list = new ArrayList<Button>();

    public void init(TableLayout stk, int i, String titl, String by) {

        TableRow tbrow = new TableRow(this);
        TextView t0v = new TextView(this);
        t0v.setText("" + (i + 1));
        t0v.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        t0v.setBackgroundResource(R.drawable.color_shape);
        t0v.setGravity(Gravity.CENTER);
        tbrow.addView(t0v);
        final TextView t1v = new TextView(this);
        t1v.setText("" + titl + "");
        t1v.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        t1v.setBackgroundResource(R.drawable.color_shape);
        t1v.setGravity(Gravity.CENTER);
        tbrow.addView(t1v);
        final TextView t12v = new TextView(this);
        t12v.setText(" " + by + " ");
        t12v.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        t12v.setBackgroundResource(R.drawable.color_shape);
        t12v.setGravity(Gravity.CENTER);
        tbrow.addView(t12v);

        final Button t6v = new Button(this);
        t6v.setText(" view post ");
        t6v.setId(i + 1);
        t6v.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        t6v.setGravity(Gravity.CENTER);
        // Set click listener for button
        t6v.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Post :" + t1v.getText().toString(), Toast.LENGTH_SHORT).show();
                final DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("");
                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot vinesnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : vinesnapshot.child("Blogzone").getChildren()) {
                                System.out.println(dataSnapshot1.getKey());
                                if (vinesnapshot.child("Blogzone").exists()) {
                                    String add=dataSnapshot1.getKey().toString();
                                        Intent i = new Intent(ViewActivity.this, SinglepostActivity.class);
                                        i.putExtra("by",t12v.getText().toString());
                                        i.putExtra("title",t1v.getText().toString());
                                        i.putExtra("child", child);
                                        startActivity(i);

                                } else {
                                    Toast.makeText(ViewActivity.this, "No posts", Toast.LENGTH_SHORT).show();
                                    f = true;
                                    break;
                                }
                            }
                            if (f)
                                break;
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        tbrow.addView(t6v);

        stk.addView(tbrow);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("");
        firebaseAuth = FirebaseAuth.getInstance();
        final List<String> areas = new ArrayList<String>();
        Intent intent = getIntent();
        child = getIntent().getExtras().getString("email");

        final TableLayout stk = (TableLayout) findViewById(R.id.myTableLayout);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Sl.No ");
        tv0.setTextColor(Color.parseColor("#000000"));
        tv0.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23f);
        tv0.setBackgroundResource(R.drawable.color_shape);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText("  Post Title  ");
        tv1.setTextColor(Color.parseColor("#000000"));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23f);
        tv1.setBackgroundResource(R.drawable.color_shape);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("  Posted By  ");
        tv2.setTextColor(Color.parseColor("#000000"));
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23f);
        tv2.setBackgroundResource(R.drawable.color_shape);
        tbrow0.addView(tv2);
        stk.addView(tbrow0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.getKey();
                    if (dataSnapshot.child(areaName).child("Blogzone").getChildren() != null)
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.child(areaName).child("Blogzone").getChildren()) {
                            final String title = (dataSnapshot1.child("title").getValue().toString());

                            final DatabaseReference refu = FirebaseDatabase.getInstance().getReference().child("");
                            refu.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        System.out.println("data:" + dataSnapshot1);
                                        if (dataSnapshot1.child("Blogzone").exists()) {
                                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("Blogzone").getChildren()) {
                                                System.out.println("cgefgh:" + title + dataSnapshot2.child("title").getValue().toString() + "      " + (dataSnapshot2.child("title").getValue().toString()).equals(title));
                                                if ((dataSnapshot2.child("title").getValue().toString()).equals(title)) {
                                                    System.out.println(dataSnapshot1.child("Blogzone").getValue());
                                                    final String email = dataSnapshot1.getKey();
                                                    final DatabaseReference referen = FirebaseDatabase.getInstance().getReference().child("");

                                                    referen.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                                                                System.out.println("dat123:" + areaSnapshot.getKey());
                                                                if ((areaSnapshot.getKey().toString().equals(email))) {
                                                                    totfieldofficer = (areaSnapshot.child("personal details").child("fname").getValue().toString()) + (areaSnapshot.child("personal details").child("lname").getValue().toString());
                                                                    System.out.println("field_officer:" + totfieldofficer);
                                                                    init(stk, count, title, totfieldofficer);
                                                                    count++;
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {
                                                        }
                                                    });

                                                }
                                            }
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent I = new Intent(ViewActivity.this, UserActivity.class);
        I.putExtra("email",child);
        finish();
        startActivity(I);


    }


}
