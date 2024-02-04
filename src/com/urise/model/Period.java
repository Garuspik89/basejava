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

    public void setFirstDate(LocalDate firstDate) {
        this.firstDate = firstDate;
    }

    public void setSecondDate(LocalDate secondDate) {
        this.secondDate = secondDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFirstDate() {
        return firstDate;
    }

    public LocalDate getSecondDate() {
        return secondDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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
