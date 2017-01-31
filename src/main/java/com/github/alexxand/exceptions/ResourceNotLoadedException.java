package com.github.alexxand.exceptions;

public class ResourceNotLoadedException extends RuntimeException {

    public ResourceNotLoadedException(){
        super();
    }

    public ResourceNotLoadedException(String message){
        super(message);
    }

    public ResourceNotLoadedException(Throwable e){
        super(e);
    }

    public ResourceNotLoadedException(String message, Throwable e){
        super(message,e);
    }
}
