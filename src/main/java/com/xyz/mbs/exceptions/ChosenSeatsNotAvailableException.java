package com.xyz.mbs.exceptions;

public class ChosenSeatsNotAvailableException extends RuntimeException{
    public ChosenSeatsNotAvailableException(){
        super("Chosen seats are not available, please try to book again!!");
    }
}
