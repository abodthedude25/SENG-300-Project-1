package com.thelocalmarketplace.hardware.test;

import static org.junit.Assert.*;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;

public class AbstractCoinDispenserTest {

    @Test
    public void testSize() {
        // Write test cases for size method
    }

    @Test
    public void testLoad() {
        // Write test cases for load method
    }

    @Test
    public void testUnload() {
        // Write test cases for unload method
    }

    @Test
    public void testGetCapacity() {
        // Write test cases for getCapacity method
    }

    @Test
    public void testReceive() {
        // Write test cases for receive method
    }

    @Test
    public void testEmit() {
        // Write test cases for emit method
    }

    @Test
    public void testHasSpace() {
        // Write test cases for hasSpace method
    }

    @Test(expected = DisabledException.class)
    public void testReject() throws DisabledException, CashOverloadException {
        // Write test cases for reject method
    }
}

