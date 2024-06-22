package com.example.GetRide.Exception;

public class CabNotFoundException extends RuntimeException {
    public CabNotFoundException(String message){
        super(message);
    }
}
