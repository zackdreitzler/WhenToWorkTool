package com.zack.UI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Dialog window to handle input of username and password.
 *
 * @author Zack Dreitzler
 * @version 1
 */
public class LoginDialogController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;


    public void setDetails(List<String> details){
        String user = username.getText();
        String pass = password.getText();
        details.add(user);
        details.add(pass);
    }
}
