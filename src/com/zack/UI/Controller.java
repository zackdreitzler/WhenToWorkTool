package com.zack.UI;

import com.zack.dataModel.Shift;
import com.zack.dataModel.ShiftData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private TableView<Shift> shiftsToAdd;

    private ShiftData shiftData;

    @FXML
    private BorderPane mainWindow;

    public void initialize(){
        shiftData = new ShiftData();
        shiftData.readContacts();
        shiftsToAdd.setItems(shiftData.getShiftList());
    }

    /**
     * Adds a shift to the list.
     */
    @FXML
    public void addShift(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Add a Shift");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("shiftDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            ShiftDialogController shiftDialogController = fxmlLoader.getController();
            Shift shift = shiftDialogController.getShift();
            shiftData.getShiftList().add(shift);
        }
    }

    /**
     * Edits the selected shift.
     */
    @FXML
    public void editShift(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Edit a Shift");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("shiftDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        ShiftDialogController shiftDialogController = fxmlLoader.getController();
        Shift selectedShift = shiftsToAdd.getSelectionModel().getSelectedItem();
        shiftDialogController.showEditShiftDialog(selectedShift);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            shiftDialogController.editShift(selectedShift);
        }
    }

    /**
     * Removes the selected shift from the list.
     */
    @FXML
    public void removeShift(){
        Shift selectedShift = shiftsToAdd.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Shift");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove shift: \n" + selectedShift);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            shiftData.getShiftList().remove(selectedShift);
        }
    }

    /**
     * Adds shifts in list to whentowork.
     */
    public void addShiftsToW2W(){

    }
}
