package com.kmitl.tulaya.foodchecking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kmitl.tulaya.foodchecking.R;

/**
 * Created by phatipan on 24/4/2018 AD.
 */

public class FoodMenuActicity extends AppCompatActivity {

    private ImageView iv;
    private TextView data1;
    private TextView data2;
    private String key = null;
    private DatabaseReference mRootRef;
    private ValueEventListener mValueEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmenu);
        iv = findViewById(R.id.TvSE);
        data1 = findViewById(R.id.data1);
        data2 = findViewById(R.id.data2);
        mRootRef = FirebaseDatabase.getInstance().getReference();


        Intent intent = getIntent();
        key = intent.getStringExtra("Key");
    }

    @Override
    protected void onStart() {
        super.onStart();

        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String data = (dataSnapshot.child(key).child("ชื่อ").getValue(String.class));
                data1.setText("ชื่อสินค้า : " + data);
                String text = "ส่วนผสม : ";
                for (int i = 1; i <= dataSnapshot.child(key).child("ส่วนผสม").getChildrenCount(); i++) {
                    data = (dataSnapshot.child(key).child("ส่วนผสม").child(String.valueOf(i)).getValue(String.class));
                    text += "\n"+i+"."+data;
                }
                data2.setText(text);
                String url = (dataSnapshot.child(key).child("รูป").getValue(String.class));
                setImage(url);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplication(), "Failed: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        };
        mRootRef.addValueEventListener(mValueEventListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mValueEventListener != null) {
            mRootRef.removeEventListener(mValueEventListener);
        }
    }

    private void setImage(String url) {
        Glide.with(getApplicationContext())
                .load(url)
                .into(iv);
    }


}