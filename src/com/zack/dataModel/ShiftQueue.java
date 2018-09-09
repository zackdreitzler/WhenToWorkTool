package com.zack.dataModel;

import java.util.LinkedList;
import java.util.Queue;

/**
 * com.zack.DataModel.ShiftQueue forms a queue of shifts that will be added
 * to the whentowork schedule.
 * @author Zack Dreitzler
 * @version 0.1
 */
public class ShiftQueue{
    private Queue<Shift> shiftQueue;

    public ShiftQueue() {
        shiftQueue = new LinkedList<>();
    }

    /**
     * Adds a shift to the back of the shiftQueue.
     * @param shift The com.zack.DataModel.Shift to be added to the queue.
     * @return true if shift is added successfully and false otherwise.
     */
    public boolean add(Shift shift){
        if(shift != null){
            shiftQueue.add(shift);
            return true;
        }
        return false;
    }

    /**
     * Removes the head of the shiftQueue.
     * @return shift The com.zack.DataModel.Shift at the head of the shiftQueue. Null is returned
     * if the queue is empty.
     */
    public Shift remove(){
        if(!shiftQueue.isEmpty()){
            return shiftQueue.remove();
        }
        return null;
    }

    /**
     * Checks the front of the Queue and returns its value.
     * @return com.zack.DataModel.Shift that is at the front of the queue. If null is present, null is returned.
     */
    public Shift peek(){
        return shiftQueue.peek();
    }

    /**
     * Checks whether the shiftQueue is empty or not.
     * @return true if queue is empty and false otherwise.
     */
    public boolean isEmpty(){
        return shiftQueue.isEmpty();
    }

}
