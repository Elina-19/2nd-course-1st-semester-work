package ru.itis.zagidullina.readl.exceptions;

public class NotAvailablePasswordException extends RuntimeException{
    public NotAvailablePasswordException(String message){
        super(message);
    }
}
