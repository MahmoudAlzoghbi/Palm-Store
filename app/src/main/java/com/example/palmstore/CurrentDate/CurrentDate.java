package com.example.palmstore.CurrentDate;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {

    public String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentData = dateFormat.format(date);

        currentData = currentData.substring(0 , currentData.indexOf(" "));
        return currentData;
    }

}
