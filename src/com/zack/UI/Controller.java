package com.zack.UI;

import com.zack.dataModel.Shift;
import com.zack.dataModel.ShiftData;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class Controller {
    @FXML
    private TableView<Shift> shiftsToAdd;
    private ShiftData shiftData;

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

    }

    /**
     * Edits the selected shift.
     */
    @FXML
    public void editShift(){

    }

    /**
     * Removes the selected shift from the list.
     */
    @FXML
    public void removeShift(){

    }

    /**
     * Adds shifts in list to whentowork.
     */
    public void addShiftsToW2W(){

    }
}
