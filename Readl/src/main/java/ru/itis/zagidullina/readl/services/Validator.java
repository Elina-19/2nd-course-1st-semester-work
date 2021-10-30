package ru.itis.zagidullina.readl.services;

import java.util.regex.Pattern;

public class Validator {

    private static final Pattern patternEmpty = Pattern.compile("\\s+");

    public boolean isNull(String str){
        if (str == null){
            return true;
        }
        else return false;
    }

    public boolean isEmpty(String str){
        if (str.equals("") || patternEmpty.matcher(str).find()){
            return true;
        }
        else return false;
    }
}
