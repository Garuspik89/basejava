package com.urise.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public  static LocalDate unmarshal(String str) {
        return LocalDate.parse(str);
    }
}

