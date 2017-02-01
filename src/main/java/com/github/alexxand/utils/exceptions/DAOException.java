package com.github.alexxand.utils.exceptions;

public class DAOException extends RuntimeException {
    public DAOException(){
        super();
    }

    public DAOException(String message){
        super(message);
    }

    public DAOException(Throwable e){
        super(e);
    }

    public DAOException(String message, Throwable e){
        super(message,e);
    }
}
