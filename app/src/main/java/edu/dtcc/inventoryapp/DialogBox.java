package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.app.Dialog;
import android.widget.EditText;

import static android.support.v7.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * Created by Kevin on 12/3/17.
 */

public class DialogBox {
    Activity activity;
    Dialog dialog;
    DialogBox dialogBox;

    public DialogBox(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);

    }
    public void newDialogBox(int layout){
        initializeDialogElements(layout);
    }

    public void setDialogBox(DialogBox dialogBox) {
        this.dialogBox = dialogBox;
    }

    private void initializeDialogElements(int layout) {
        dialog.setContentView(layout);
        dialog.getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
        dialog.show();
    }

    public void endDialogBox(){
        dialog.cancel();
    }
    public void collectFolderName(FolderData folderData){
        EditText newFolderName = (EditText) dialog.findViewById(R.id.new_folder_name);
        folderData.setName(newFolderName.getText().toString());
    }

    public void collectNewFolderName(FolderData folderData){
        EditText newFolderName = (EditText) dialog.findViewById(R.id.updated_folder_name);
        folderData.setNewName(newFolderName.getText().toString());
    }

}
