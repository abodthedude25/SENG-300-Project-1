package com.thelocalmarketplace.demo;

import com.thelocalmarketplace.software.OutOfInkException;

public class OutOfInkExceptionDemo {

    public static void main(String[] args) {
        try {
            // Simulate a scenario where the printer runs out of ink
            throw new OutOfInkException("Printer is out of ink. Please replace the ink cartridge.");
        } catch (OutOfInkException e) {
            // Handle the OutOfInkException
            System.out.println("Error: " + e.getMessage());
            // Take appropriate actions, such as notifying the user or logging the error
        }
    }
}
