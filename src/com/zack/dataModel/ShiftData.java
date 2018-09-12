package com.zack.dataModel;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * ShiftData is for loading the shifts into the Shift queue.
 */
public class ShiftData {
    private  ShiftQueue shiftQueue;
    private  ObservableList<Shift> shiftObservableList;
    private final String INPUT_FILE = "shifts_to_add.txt";

    public ShiftData(){
        shiftQueue = new ShiftQueue();
        //todo Observable list
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
            shiftQueue.printQueue();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a shift to shiftQueue with details found in
     * the shiftInformation array.
     * @param shiftInformation array of strings holding details for the shift
     */
    private void addShift(String[] shiftInformation) {
        String date = shiftInformation[0];
        String dayOfWeek = shiftInformation[1];
        String courseType = shiftInformation[4];
        String time = shiftInformation[6];
        String staffType = getStaffType(courseType);

        if(courseType.equalsIgnoreCase("lows") ||
            courseType.equalsIgnoreCase("ody")){
            int numCommas = getNumCommas(shiftInformation[5]);
            int numFacilitatorShifts = (numCommas+2)/2;
            int numApprenticeShifts = (numFacilitatorShifts-1 == 0) ? 1 : (numFacilitatorShifts-1);
            Shift facilShift = new Shift(date, dayOfWeek, courseType, time, staffType, numFacilitatorShifts);
            Shift apprenticeShift = new Shift(date, dayOfWeek, courseType,
                                                time, "Apprentice", numApprenticeShifts);
            shiftQueue.add(facilShift);
            shiftQueue.add(apprenticeShift);
        }else {
            //add 1 shift //TODO
        }
    }

    /**
     * Gets the number of commas in the given string.
     * @param s is the string passed containing commas
     * @return num number of commas in the string s
     */
    private int getNumCommas(String s) {
        int numCommas = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == ',') numCommas++;
        }
        if(numCommas == 0) return 1;
        return numCommas;
    }

    /**
     * Finds the type of staff needed for a given course type
     * @param courseType type of course that this shift is
     * @return string for the sype of staff needed for this shift
     *          returns null if courseType is not a valid course type
     */
    private String getStaffType(String courseType) {
        if(courseType.equalsIgnoreCase("lows") ||
            courseType.equalsIgnoreCase("ody")){
            return "Facilitator";
        }else if (courseType.equalsIgnoreCase("tower")){
            return "Tower Tech";
        } else if (courseType.equalsIgnoreCase("zips")){
            return "Zip Tech";
        }else{
            return null;
        }
    }
}
