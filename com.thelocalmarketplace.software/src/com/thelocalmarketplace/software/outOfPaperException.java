package com.thelocalmarketplace.software;

public class outOfPaperException extends Exception {
    public outOfPaperException(String paperErrorMessage) {
        super(paperErrorMessage);
    }

}
