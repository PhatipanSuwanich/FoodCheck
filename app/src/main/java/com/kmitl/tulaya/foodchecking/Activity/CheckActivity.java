package com.kmitl.tulaya.foodchecking.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kmitl.tulaya.foodchecking.CheckModel;
import com.kmitl.tulaya.foodchecking.CustomViewGroup;
import com.kmitl.tulaya.foodchecking.R;
import com.kmitl.tulaya.foodchecking.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phatipan on 19/4/2018 AD.
 */

public class CheckActivity extends AppCompatActivity {

    public static final List<CheckModel> check = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        ListView listView = (ListView) findViewById(R.id.litsview);

        final CustomViewGroup adapter = new CustomViewGroup(this, MainActivity.users);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                UserModel model = MainActivity.users.get(i);

                if (model.isSelected()) {
                    model.setSelected(false);
                    check.get(i).setCkName(null);
                } else {
                    model.setSelected(true);
                    check.get(i).setCkName(MainActivity.users.get(i).getUserName());
                }

                MainActivity.users.set(i, model);

                //now update adapter
                adapter.updateRecords(MainActivity.users);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
