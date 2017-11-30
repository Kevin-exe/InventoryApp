package edu.dtcc.inventoryapp;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton addButton;
    Dialog myDialog;
    Button addone, addtwo, close;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (ImageButton)findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CustomAlertDiaglog();
            }
        });


    }
    public void CustomAlertDiaglog()
    {
        myDialog = new Dialog(MainActivity.this);
        myDialog.setContentView(R.layout.customdialog);
        myDialog.setTitle("My Custom Dialog Box");

        addone = (Button)myDialog.findViewById(R.id.addone);
        addtwo = (Button)myDialog.findViewById(R.id.addtwo);
        close = (Button)myDialog.findViewById(R.id.close);

        addone.setEnabled(true);
        addtwo.setEnabled(true);
        close.setEnabled(true);

        myDialog.getWindow().setLayout(750, 1300);

        addone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(), "Testing add one", Toast.LENGTH_LONG).show();
            }
        });

        addtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(), "Testing add two", Toast.LENGTH_LONG).show();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.cancel();
            }
        });

        myDialog.show();
    }
}
