package com.qlyshopphone_backend.exceptions;
import static com.qlyshopphone_backend.constant.ErrorMessage.*;

public class DataNotFoundException extends Exception{
    public DataNotFoundException(String message){
        super(message);
    }
}
