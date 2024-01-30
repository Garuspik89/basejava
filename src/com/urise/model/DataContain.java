package com.urise.model;

import java.time.LocalDate;

public class DataContain {

    private LocalDate firstDate;
    private  LocalDate secondDate;
    private  String title;
    private  String description;

    public DataContain(LocalDate firstDate, LocalDate secondDate, String title, String description) {
        this.firstDate = firstDate;
        this.secondDate = secondDate;
        this.title = title;
        this.description = description;
    }

}
