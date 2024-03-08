package com.thelocalmarketplace.software;

public class outOfInkException extends Exception {
    public outOfInkException(String inkErrorMessage){
        super(inkErrorMessage);
    }

}
