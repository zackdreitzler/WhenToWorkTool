package com.zack.dataModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * ShiftData is for loading the shifts into the list of shifts
 */
public class ShiftData {
    private ObservableList<Shift> shiftList;
    private final String INPUT_FILE = "shifts_to_add.txt";

    public ShiftData(){
        shiftList = FXCollections.observableArrayList();
    }

    public ObservableList<Shift> getShiftList(){
        return shiftList;
    }

    /**
     * Reads in the contacts from the input file.
     */
    public void readContacts(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT_FILE))){
            String shift;
            String[] shiftToAdd;
            while((shift = bufferedReader.readLine()) != null){
                shiftToAdd = shift.split("\t");
                SimpleStringProperty[] shiftInformation = new SimpleStringProperty[15];
                for(int i = 0; i < shiftToAdd.length; i++){
                    SimpleStringProperty value = new SimpleStringProperty(shiftToAdd[i]);
                    shiftInformation[i] = value;
                }
                addShift(shiftInformation);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a shift to shiftList with details found in
     * the shiftInformation array.
     * @param shiftInformation array of strings holding details for the shift
     */
    private void addShift(SimpleStringProperty[] shiftInformation) {
        SimpleStringProperty date = shiftInformation[0];
        SimpleStringProperty dayOfWeek = shiftInformation[1];
        SimpleStringProperty courseType = shiftInformation[4];
        SimpleStringProperty time = shiftInformation[6];
        SimpleStringProperty staffType = getStaffType(courseType);
        int numCommas = getNumCommas(shiftInformation[5]);

        if (courseType.get().equalsIgnoreCase("tower") ||
            courseType.get().equalsIgnoreCase("zips") ||
        courseType.get().isEmpty()){
            SimpleIntegerProperty numShifts = new SimpleIntegerProperty(numCommas+1);
            Shift shiftsToAdd = new Shift(date, dayOfWeek, courseType, time, staffType, numShifts);
            shiftList.add(shiftsToAdd);
        }else{
            int numFacilitatorShifts;
            int numApprenticeShifts;
            if((numCommas+2)%2 == 0){
                numFacilitatorShifts = (numCommas+2)/2;
                numApprenticeShifts = numFacilitatorShifts;
            }else{
                numFacilitatorShifts = ((numCommas+2)/2)+1;
                numApprenticeShifts = (numFacilitatorShifts-1 == 0) ? 1 : (numFacilitatorShifts-1);
            }

            Shift facilShift = new Shift(date, dayOfWeek, courseType,
                    time, staffType, new SimpleIntegerProperty(numFacilitatorShifts));

            Shift apprenticeShift = new Shift(date, dayOfWeek, courseType, time,
                    new SimpleStringProperty("Apprentice"), new SimpleIntegerProperty(numApprenticeShifts));

            shiftList.add(facilShift);
            shiftList.add(apprenticeShift);
        }
    }

    /**
     * Gets the number of commas in the given string.
     * @param str is the string passed containing commas
     * @return num number of commas in the string s
     */
    private int getNumCommas(SimpleStringProperty str) {
        int numCommas = 0;
        String commasToCount = str.get();
        for(int i = 0; i < commasToCount.length(); i++){
            if(commasToCount.charAt(i) == ',') numCommas++;
        }
        if(numCommas == 0) return 1;
        return numCommas;
    }

    /**
     * Finds the type of staff needed for a given course type
     * @param courseType type of course that this shift is
     * @return SimpleStingPropert for the type of staff needed for this shift
     *          returns null if courseType is not a valid course type
     */
    private SimpleStringProperty getStaffType(SimpleStringProperty courseType) {
        if (courseType.get().equalsIgnoreCase("tower")){
            return new SimpleStringProperty("Tower Tech");
        }else if(courseType.get().equalsIgnoreCase("zips") ||
                courseType.get().isEmpty()){
            return new SimpleStringProperty("Zip Tech");
        }else {
            return new SimpleStringProperty("Facilitator");
        }
    }
}
