package com.lcwd.user.service.Exceptions;

public class ResourceNotFoundExceptions extends RuntimeException{

    //extra properties that you want to manage
    public ResourceNotFoundExceptions(){
        super("Resource not found on server!!");
    }

    public ResourceNotFoundExceptions(String message){
        super(message);
    }
}
