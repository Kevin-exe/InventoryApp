package edu.dtcc.inventoryapp;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;

import static android.support.v7.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * Created by Kevin on 12/3/17.
 */

public class DialogBox {
    Activity activity;
    Dialog dialog;

    public DialogBox(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
    }
    public void newDialogBox(int layout){
        initializeDialogElements(layout);
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
        EditText newFolderName = (EditText) dialog.findViewById(R.id.folder_input);
        folderData.setName(newFolderName.getText().toString());
    }
}
