package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Dialog;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity {
    Context context;
    Activity activity;
    Dialog myDialog;
    Button create_folder, create_file, close;

    //Temporary testing ArrayLists for folderContent
    ArrayList<String> folderContentNames = new ArrayList<>();
    ArrayList<String> folderContentTypes = new ArrayList<>();

    // ArrayList of strings to hold the list items
    ArrayList<String> list = new ArrayList<>();

    // custom list adapter that will handle the list
    CustomListAdapter adapter;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_screen);


    }

    private void updateContexts(){
        context = getApplicationContext();
        activity = MainActivity.this;
    }

    public void displayDialog(View button) {
        updateContexts();
        switch (button.getId()) {
            case R.id.addButton: customAlertDialog(); break;
        }
    }
    private void createListView(){
        // initialize custom adapter
        adapter = new CustomListAdapter(list, context, activity);

        // initialize the list and set the adapter
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        // onClick listener for the listView (not the edit buttons)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                position++;
                int count = adapter.getItemCount();

                theToasting(position + " out of " + count);
            }
        });
    }

    public void customAlertDialog()
    {
        myDialog = new Dialog(activity);
        myDialog.setContentView(R.layout.customdialog);
        myDialog.setTitle("My Custom Dialog Box");

        create_folder = (Button)myDialog.findViewById(R.id.create_folder);
        create_file = (Button)myDialog.findViewById(R.id.create_file);
        close = (Button)myDialog.findViewById(R.id.close);

        create_folder.setEnabled(true);
        create_file.setEnabled(true);
        close.setEnabled(true);

        myDialog.getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);

        create_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                adapter.addItems();
            }
        });

        //temporary bug. Change to file creation later instead of delete.
        create_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                adapter.deleteItems(0);
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

    public void sqlOption(View button) {
        updateContexts();

        TestZone testing = new TestZone(context, activity);
        ArrayList<ArrayList<String>> folderContent = new ArrayList<>();


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
                case R.id.get_contents:
                    folderContent.addAll(testing.getFolderContent("Home"));
                    folderContentNames.addAll(folderContent.get(0));
                    folderContentTypes.addAll(folderContent.get(1));

                    for (int x = 0; x < folderContentNames.size(); x++) {
                        System.out.println("Folder: " + folderContentNames.get(x) + "\tType: " + folderContentTypes.get(x));
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void nextMenu(View button){
        updateContexts();

        switch (button.getId()) {
            case R.id.title_button: setContentView(R.layout.activity_main);
                                    createListView(); break;
            case R.id.db_testing: setContentView(R.layout.sql_testing); break;
        }
    }


    public void toMainMenu(View button) {
        setContentView(R.layout.activity_main);
        updateContexts();
    }

    private void theToasting(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
