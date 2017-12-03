package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Dialog;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity {
    Context context;
    Activity activity;
    Dialog myDialog;
    Dialog myFolderDialog;
    Button create_folder, create_file, close, submit, cancelBtn;
    FolderContent folderContent;
    FolderData folderData;
    FileData fileData;
    DatabaseAdder dbAdder;
    DatabaseReader dbReader;
    EditText newFolderName;

    // ArrayList of strings to hold the list items
    ArrayList<String> list = new ArrayList<>();

    // custom list adapter that will handle the list
    CustomListAdapter adapter;

    // ListView for displaying the list
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_screen);

        folderData = new FolderData();
        folderData.setParent("Home");
    }

    private void updateContexts(){
        context = getApplicationContext();
        activity = MainActivity.this;
    }

    public void displayDialog(View button) {
        updateContexts();
        switch (button.getId()) {
            case R.id.addButton: customAlertDialog(); break;
            case R.id.create_folder: createFolderDialogBox();
        }
    }

    private void createListView(String directory){
        //Get home directory values
        dbReader = new DatabaseReader(context);
        folderContent = dbReader.getFolderContent(directory);
        list.clear();
        list.addAll(folderContent.getNames());

        // initialize custom adapter
        adapter = new CustomListAdapter(list, folderContent, context, activity);

        // initialize the list and set the adapter
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        // onClick listener for the listView (not the edit buttons)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                int count = list.size();

                if (folderContent.getTypes().get(position).equals("Folder")) {
                    String newDirectory = list.get(position);
                    folderData = new FolderData();
                    folderData.setParent(newDirectory);
                    createListView(newDirectory);
                }
                else
                    System.out.println("Item clicked is not a folder");

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


        create_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //todo change menu for file creation input. likely needs the directory name for creation
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

    private void createFolderDialogBox(){
        myFolderDialog = new Dialog(activity);
        myFolderDialog.setContentView(R.layout.create_folder_dialog);
        myFolderDialog.setTitle("My Custom Dialog Box Test");

        newFolderName = (EditText) myFolderDialog.findViewById(R.id.folder_input);
        submit = (Button) myFolderDialog.findViewById(R.id.submit_folder_Btn);
        cancelBtn = (Button) myFolderDialog.findViewById(R.id.cancelBtn);

        newFolderName.setEnabled(true);
        submit.setEnabled(true);
        cancelBtn.setEnabled(true);

        myFolderDialog.getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);

        // dismiss original dialog box and display new
        myDialog.dismiss();
        myFolderDialog.show();

        // on click for submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String parent = folderData.getParent();
                    DatabaseAdder dbAdder = new DatabaseAdder(context);
                    folderData.setName(newFolderName.getText().toString());
                    dbAdder.createNewFolder(folderData);
                    myFolderDialog.cancel();
                    createListView(parent);
                } catch (Exception e){
                    theToasting("Field cannot be empty");
                }
            }
        });

        // on click for cancel button
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFolderDialog.cancel();
            }
        });
    }

    // button handler
    public void sqlOption(View button) {
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
                    break;
                case R.id.delete_all:
                    testing.deleteFoldersTable();
                    new FolderContent();
                    break;
                case R.id.get_contents:
                    folderContent = dbReader.getFolderContent("Home");

                    for (int x = 0; x < folderContent.getNames().size(); x++) {
                        System.out.println("Folder: " + folderContent.getNames().get(x) + "\tType: " + folderContent.getTypes().get(x));
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // method that loads the DB testing screen
    public void nextMenu(View button){
        updateContexts();

        switch (button.getId()) {
            case R.id.title_button: setContentView(R.layout.activity_main);
                                    createListView("Home"); break;
            case R.id.db_testing: setContentView(R.layout.sql_testing); break;
        }

        updateContexts();
    }

    // method that loads the main menu screen
    public void toMainMenu(View button) {
        setContentView(R.layout.activity_main);
        updateContexts();
        createListView("Home");
        folderData.setParent("Home");
    }

    // method that creates toast messages with the passed string
    private void theToasting(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
