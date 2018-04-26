package com.kmitl.tulaya.foodchecking.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kmitl.tulaya.foodchecking.CheckModel;
import com.kmitl.tulaya.foodchecking.R;
import com.kmitl.tulaya.foodchecking.UserModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_QR_SCAN = 4;
    private Button buttonIntent;
    private Button buttonCheck;
    public static final List<UserModel> users = new ArrayList<>();
    private DatabaseReference mRootRef;
    private ValueEventListener mValueEventListener;
    private String data;
    private String contents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        buttonIntent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                startActivityForResult(Intent.createChooser(intent, "Scan with"), REQUEST_QR_SCAN);
            }
        });


        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CheckActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode
            , Intent intent) {
        if (requestCode == REQUEST_QR_SCAN && resultCode == RESULT_OK) {
            contents = intent.getStringExtra("SCAN_RESULT");
            String content = contents.substring(8, 12);
            String con = contents.substring(0, 8);
            int pk = Integer.parseInt(content);
            int pk1 = pk - 0000;
            if (con.equals("85580111") && pk1 >= 0 && pk1 <= 40) {
                mValueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int win = 0;
                        outerloop:
                        for (int j = 1; j <= dataSnapshot.child(contents).child("ส่วนผสม").getChildrenCount(); j++) {
                            String all = (dataSnapshot.child(contents).child("ส่วนผสม").child(String.valueOf(j)).getValue(String.class));
                            for (int i = 0; i < CheckActivity.check.size(); i++) {
                                if (all.equals(CheckActivity.check.get(i).getCkName())) {
                                    show("อาหารนี้มีส่วนผสมที่คุณแพ้!!");
                                    win = 1;
                                    break outerloop;
                                }
                            }
                        }
                        if (win == 0)
                            show("ไม่พบส่วนผสมที่คุณแพ้");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                mRootRef.addValueEventListener(mValueEventListener);
            } else
                Toast.makeText(getApplication(), "ไม่พบข้อมูลสินค้า", Toast.LENGTH_SHORT).show();
        }
    }

    private void show(String text) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(text);
        builder.setPositiveButton("ดูส่วนประผสม", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(MainActivity.this, FoodMenuActicity.class);
                intent.putExtra("Key", contents);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void init() {
        buttonIntent = findViewById(R.id.btScan);
        buttonCheck = findViewById(R.id.btCheck);

        mRootRef = FirebaseDatabase.getInstance().getReference();

        if (users.size() == 0) {
            mValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //อ่านค่าจากเบสขึ้นมาให้ผู้ใช้เลือก
                    for (int i = 1; i <= dataSnapshot.child("แพ้").getChildrenCount(); i++) {
                        data = (dataSnapshot.child("แพ้").child(String.valueOf(i)).getValue(String.class));
                        users.add(new UserModel(false, data));
                        CheckActivity.check.add(new CheckModel(null));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplication(), "Failed: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                }
            };
            mRootRef.addValueEventListener(mValueEventListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mValueEventListener != null) {
            mRootRef.removeEventListener(mValueEventListener);
        }
    }
}
