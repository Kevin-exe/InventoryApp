package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.app.Dialog;
import android.widget.EditText;

import static android.support.v7.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * Created by Kevin on 12/3/17.
 */

class DialogBox {
    private Dialog dialog;

    DialogBox(Activity activity) {
        dialog = new Dialog(activity);

    }
    void newDialogBox(int layout){
        initializeDialogElements(layout);
    }

    private void initializeDialogElements(int layout) {
        dialog.setContentView(layout);
        dialog.getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
        dialog.show();
    }

    void endDialogBox(){
        dialog.cancel();
    }
    void collectFolderName(FolderData folderData){
        EditText newFolderName = (EditText) dialog.findViewById(R.id.new_folder_name);
        folderData.setName(newFolderName.getText().toString());
    }

    void collectNewFolderName(FolderData folderData){
        EditText newFolderName = (EditText) dialog.findViewById(R.id.updated_folder_name);
        folderData.setNewName(newFolderName.getText().toString());
    }

}
