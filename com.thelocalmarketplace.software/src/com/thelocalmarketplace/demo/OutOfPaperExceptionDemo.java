package com.thelocalmarketplace.demo;

import com.thelocalmarketplace.software.OutOfPaperException;

public class OutOfPaperExceptionDemo {

    public static void main(String[] args) {
        try {
            // Simulate a scenario where the paper runs out
            throw new OutOfPaperException("Printer is out of paper. Please refill.");
        } catch (OutOfPaperException e) {
            // Handle the outOfPaperException
            System.out.println("Error: " + e.getMessage());
            // Take appropriate actions, such as notifying the user or logging the error
        }
    }
}
