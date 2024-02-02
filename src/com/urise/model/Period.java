package com.urise.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private LocalDate firstDate;
    private  LocalDate secondDate;
    private  String title;
    private  String description;

    @Override
    public String toString() {
        return "Period{" +
                "firstDate=" + firstDate +
                ", secondDate=" + secondDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(firstDate, period.firstDate) && Objects.equals(secondDate, period.secondDate) && Objects.equals(title, period.title) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstDate, secondDate, title, description);
    }
}
