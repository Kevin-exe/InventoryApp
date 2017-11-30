package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Context context;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_screen);
    }

    private void updateContexts(){
        context = getApplicationContext();
        activity = MainActivity.this;
    }

    protected void nextMenu(View button){
        updateContexts();

        switch (button.getId()) {
            case R.id.title_button: setContentView(R.layout.activity_main); break;
            case R.id.db_testing: setContentView(R.layout.sql_testing); break;
        }
    }

    protected void sqlOption(View button) {
        updateContexts();

        TestZone testing = new TestZone(context, activity);

        try {
            switch (button.getId()) {
                case R.id.select_all:
                    testing.selectAll();
                    break;
                case R.id.select_testing:
                    testing.getFromDB();
                    break;
                case R.id.add_folder:
                    testing.createNewFolder();
                    break;
                case R.id.delete_all:
                    testing.deleteDB();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void toMainMenu(View button) {
        setContentView(R.layout.activity_main);
    }
}
