package com.appscyclone.themoviedb.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by TDP on 07/01/2018.
 */

public class ConvertNumber {
    public static String Convert(int number){
        NumberFormat numberFormat=NumberFormat.getNumberInstance(Locale.US);
        return numberFormat.format(number)+"$";
    }
}
