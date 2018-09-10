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

    public ShiftData(){

    }

    /**
     * Reads in the contacts from the input file.
     */
    public void readContacts(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("shifts_to_add.txt"))){
            String shift = bufferedReader.readLine();
            System.out.println(shift);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
