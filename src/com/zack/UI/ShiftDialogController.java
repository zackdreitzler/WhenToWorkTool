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

    /**
     * Takes the contents of the fields and returns a new shift with those fields.
     * @return shift that contains the fields provided in the text fields.
     */
    public Shift getShift(){
        String date = dateField.getText();
        String dayOfWeek = dayOfWeekField.getText();
        String courseType = courseTypeField.getText();
        String time = timeField.getText();
        String staffType = staffTypeField.getText();
        int numShifts = Integer.parseInt(numShiftsField.getText());
        return new Shift(date, dayOfWeek, courseType, time, staffType, numShifts);
    }

    /**
     * Sets the dialog fields to the selected shift.
     * @param selectedShift
     */
    public void showEditShiftDialog(Shift selectedShift){
        dateField.setText(selectedShift.getDate());
        dayOfWeekField.setText(selectedShift.getDayOfWeek());
        courseTypeField.setText(selectedShift.getCourseType());
        timeField.setText(selectedShift.getTime());
        staffTypeField.setText(selectedShift.getStaffType());
        numShiftsField.setText("" + selectedShift.getNumShifts());
    }

    /**
     * Updates the selected contact with the given fields.
     * @param selectedShift
     */
    public void editShift(Shift selectedShift){
        selectedShift.setDate(dateField.getText());
        selectedShift.setDayOfWeek(dayOfWeekField.getText());
        selectedShift.setCourseType(courseTypeField.getText());
        selectedShift.setTime(timeField.getText());
        selectedShift.setStaffType(staffTypeField.getText());
        selectedShift.setNumShifts(Integer.parseInt(numShiftsField.getText()));
    }

}
