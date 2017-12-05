package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context;
    Activity activity;
    DialogBox dialogBox;
    FolderContent folderContent;
    FolderData folderData;
    FileData fileData;
    DatabaseAdder dbAdder;
    DatabaseReader dbReader;
    DatabaseUpdater dbUpdater;
    DatabaseDeleter dbDeleter;
    EditText newFolderName;
    String rootDirectory;
    String currentDirectory;
    String itemType;
    String item;
    int indexOfItem;

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
//        dialogBox = new DialogBox(activity);
        folderData.setParent("Home");
        updateActionBar();
    }

    private void updateContexts(){
        context = getApplicationContext();
        activity = MainActivity.this;
    }

    private void createListView(String directory){
        //Get home directory values
        dbReader = new DatabaseReader(context);
        folderContent = dbReader.getFolderContent(directory);
        list = (ArrayList<String>)folderContent.getNames().clone();

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

                for (String contents : list) {
                    System.out.println("contents " + contents);
                }
            }
        });
    }

    private void openFolderContents(String directory) {
        folderData = new FolderData();
        updateRoot(directory);
        updateListView();
    }

    private void openFileContents(String file) {

    }
    private void updateListView(){
        createListView(folderData.getParent());
        updateActionBar();
    }

    private void getRootDirectory(){
        if (!(folderData.getParent().equals("Home"))) {
            setContentView(R.layout.activity_main);
            currentDirectory = folderData.getParent();
            rootDirectory = dbReader.findRootDirectory(currentDirectory);
            updateRoot(rootDirectory);
            updateListView();
        }

    }
    private void updateRoot(String rootDirectory){
        folderData.setParent(rootDirectory);
    }

    private void updateActionBar() {
        getSupportActionBar().setTitle(folderData.getParent());
    }
    private void getItemAndType(){
        indexOfItem = adapter.getCurrentIndex();
        itemType = folderContent.getTypes().get(indexOfItem);
        item = folderContent.getNames().get(indexOfItem);
    }

    private void newDialogBox(int layout){
        dialogBox = new DialogBox(activity);
        dialogBox.newDialogBox(layout);
    }

    public void settingsBoxButtons(View button) {
        getItemAndType();
        dbDeleter = new DatabaseDeleter(context);
        dialogBox = dialogBox.getDialogBox();

        if (itemType.equals("Folder")) {
            switch (button.getId()) {
                case R.id.editBtn:
                    break;
                case R.id.deleteBtn:
                    dbDeleter.deleteFolder(item);
                    updateListView();
                    break;
                case R.id.closeBtn:
                    dialogBox.endDialogBox();
                    break;
            }
        } else {
            switch (button.getId()) {
                case R.id.editBtn:
                    break;
                case R.id.deleteBtn:
                    dbDeleter.deleteFile(item);
                    updateListView();
                    break;
                case R.id.closeBtn:
                    dialogBox.endDialogBox();
                    break;
            }
        }
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
                setContentView(R.layout.create_file);
                dialogBox.endDialogBox();
                break;
            case R.id.close: dialogBox.endDialogBox(); break;
        }
    }

    public void createFolderBoxButtons(View button) {
        switch (button.getId()) {
            case R.id.submit_new_file_btn: createNewFolder(); break;
            case R.id.cancelBtn: dialogBox.endDialogBox(); break;
        }
    }

    public void createFileMenu(View button) {
        String currentParent = folderData.getParent();
        switch (button.getId()) {
            case R.id.submit_new_file_btn: new NewFile(context, activity, currentParent);
            case R.id.cancel_file_btn: setContentView(R.layout.activity_main);
                createListView(currentParent);
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
        } catch (NoSuchFieldError e){
            theToasting("Field cannot be empty");
        } catch (IllegalArgumentException e){
            theToasting("Minimum character length is 4");
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
    public void changeMenu(View button){
        updateContexts();

        switch (button.getId()) {
            case R.id.title_button: setContentView(R.layout.activity_main);
                                    createListView("Home"); break;
            case R.id.db_testing: setContentView(R.layout.sql_testing); break;
            case R.id.back_button:
                getRootDirectory();
                break;
        }

        updateContexts();
    }

    // method that loads the main menu screen
    public void toMainMenu(View button) {
        setContentView(R.layout.activity_main);
        updateContexts();
        createListView("Home");
        updateRoot("Home");
        updateActionBar();
    }

    // method that creates toast messages with the passed string
    private void theToasting(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
