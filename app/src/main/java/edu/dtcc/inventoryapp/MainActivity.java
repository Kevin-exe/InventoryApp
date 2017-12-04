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

public class MainActivity extends AppCompatActivity {
    Context context;
    Activity activity;
    Dialog myDialog;
    Dialog myFolderDialog;
    DialogBox dialogBox;
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

                String selectedItem = list.get(position);
                boolean isFolder = folderContent.getTypes().get(position).equals("Folder");

                if (isFolder)
                    openFolderContents(selectedItem);
                else
                    openFileContents(selectedItem);
            }
        });
    }

    private void openFolderContents(String directory) {
        folderData = new FolderData();
        folderData.setParent(directory);
        createListView(directory);
    }

    private void openFileContents(String file) {

    }

    private void newDialogBox(int layout){
        dialogBox = new DialogBox(activity);
        dialogBox.initializeDialogElements(layout);
    }

    public void createContentBoxButtons(View button){
        switch (button.getId()) {
            case R.id.create_new_content:
                newDialogBox(R.layout.create_content_dialog);
                break;
            case R.id.create_folder:
                dialogBox.endDialogBox();
                newDialogBox(R.layout.create_folder_dialog);
                break;
            case R.id.create_file:
                //todo change menu for file creation input. likely needs the directory name for creation
                break;
            case R.id.close: dialogBox.endDialogBox(); break;
        }
    }

    public void createFolderBoxButtons(View button) {
        switch (button.getId()) {
            case R.id.submit_folder_Btn: createNewFolder(); break;
            case R.id.cancelBtn: dialogBox.endDialogBox(); break;
        }
    }

    private void createNewFolder(){
        try {
            String parent = folderData.getParent();
            DatabaseAdder dbAdder = new DatabaseAdder(context);
            dialogBox.collectFolderName(folderData);
            dbAdder.createNewFolderInDB(folderData);
            dialogBox.endDialogBox();
            createListView(parent);
        } catch (Exception e){
            theToasting("Field cannot be empty");
        }
    }

    //TESTING. Remove later. Buttons for the db_testing menu
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

    //TESTING. Remove later method that loads the DB testing screen
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
