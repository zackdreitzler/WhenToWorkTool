package com.zack.UI;

import com.zack.dataModel.Shift;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;


public class ShiftDialogController {
    @FXML
    private TextField dateField;

    @FXML
    private TextField dayOfWeekField;

    @FXML
    private TextField courseTypeField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField staffTypeField;

    @FXML
    private TextField numShiftsField;


    public Shift getShift(){
        String date = dateField.getText();
        String dayOfWeek = dayOfWeekField.getText();
        String courseType = courseTypeField.getText();
        String time = timeField.getText();
        String staffType = staffTypeField.getText();
        int numShifts = Integer.parseInt(numShiftsField.getText());
        return new Shift(date, dayOfWeek, courseType, time, staffType, numShifts);
    }
}
