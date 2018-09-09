package com.zack.dataModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShiftQueueTest {
    private Shift shift;
    private ShiftQueue shiftQueue;


    @BeforeEach
    void setUp() {
        shift = new Shift("9/12/2018", "M", "Lows",
                "900-1200", "Facilitator", 2);
        shiftQueue = new ShiftQueue();

    }

    @Test
    void add_Shift() {
        assertTrue(shiftQueue.add(shift));
    }

    @Test
    void add_Null(){
        assertFalse(shiftQueue.add(null));
    }

    @Test
    void remove_Shift() {
        shiftQueue.add(shift);
        assertNotNull(shiftQueue.remove());
    }

    @Test
    void remove_EmptyQueue(){
        assertNull(shiftQueue.remove());
    }

    @Test
    void peek_Shift() {
        shiftQueue.add(shift);
        assertNotNull(shiftQueue.peek());
    }

    @Test
    void peek_EmptyQueue(){
        assertNull(shiftQueue.peek());
    }

    @Test
    void isEmpty_NotEmpty() {
        shiftQueue.add(shift);
        assertFalse(shiftQueue.isEmpty());
    }

    @Test
    void isEmpty_Empty(){
        assertTrue(shiftQueue.isEmpty());
    }
}