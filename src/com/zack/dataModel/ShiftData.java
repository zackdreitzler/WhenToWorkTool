package com.zack.dataModel;

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
            String[] shiftInformation;
            while((shift = bufferedReader.readLine()) != null){
                shiftInformation = shift.split("\t");
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
    private void addShift(String[] shiftInformation) {
        String date = shiftInformation[0];
        String dayOfWeek = shiftInformation[1];
        String courseType = shiftInformation[4];
        String time = shiftInformation[6];
        String staffType = getStaffType(courseType);
        int numCommas = getNumCommas(shiftInformation[5]);

        if (courseType.equalsIgnoreCase("tower") ||
            courseType.equalsIgnoreCase("zips") ||
        courseType.isEmpty()){
            Shift shiftsToAdd = new Shift(date, dayOfWeek, courseType, time, staffType, numCommas+1);
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
                    time, staffType, numFacilitatorShifts);

            Shift apprenticeShift = new Shift(date, dayOfWeek, courseType,
                    time, "Apprentice", numApprenticeShifts);

            shiftList.add(facilShift);
            shiftList.add(apprenticeShift);
        }
    }

    /**
     * Gets the number of commas in the given string.
     * @param str is the string passed containing commas
     * @return num number of commas in the string s
     */
    private int getNumCommas(String str) {
        int numCommas = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == ',') numCommas++;
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
    private String getStaffType(String courseType) {
        if (courseType.equalsIgnoreCase("tower")){
            return "Tower Tech";
        }else if(courseType.equalsIgnoreCase("zips") ||
                courseType.isEmpty()){
            return "Zip Tech";
        }else {
            return "Facilitator";
        }
    }
}
