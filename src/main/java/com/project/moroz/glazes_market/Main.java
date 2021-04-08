package com.project.moroz.glazes_market;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        String pass = bCryptPasswordEncoder.encode("super");
        //System.out.println(pass);
        System.out.println((5+6+3+9+12)/3);
        System.out.println(5/3+6/3+3/3+9/3+12/3);

        String stringDate = "03/08/1995";
        String stringDate2 = "02-09-1995 23:37:50";
        //String stringDate3 = "02-May-1995";
        String stringDate4 = "04 02, 1995";
        String stringDate5 = "Thu, May 02 1995";
        String stringDate6 = "Thu, May 02 1995 23:37:50";
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat date2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        DateFormat date3 = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat date4 = new SimpleDateFormat("MM dd, yyyy");
        DateFormat date5 = new SimpleDateFormat("E, MMM dd yyyy");
        DateFormat date6 = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");
        try {
            Date nDate1 = date.parse(stringDate);
            System.out.println(stringDate + " : " + date.format(nDate1));
            Date nDate2 = date2.parse(stringDate2);
            System.out.println(stringDate2 + " : " + date2.format(nDate2));
            //Date nDate3 = date3.parse(stringDate3);
            //System.out.println(stringDate3 + " : " + date3.format(nDate3));
            Date nDate4 = date4.parse(stringDate4);
            System.out.println(stringDate4 + " : " + date4.format(nDate4));
            Date nDate5 = date5.parse(stringDate5);
            System.out.println(stringDate5 + " : " + date5.format(nDate5));
            Date nDate6 = date6.parse(stringDate6);
            System.out.println(stringDate6 + " : " + date6.format(nDate6));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
