package ru.itis.zagidullina.readl.services;

public class Validator {

    public boolean isNull(String str){
        if (str == null){
            return true;
        }
        else return false;
    }

    public boolean isEmpty(String str){
        return str.trim().isEmpty();
    }
}
