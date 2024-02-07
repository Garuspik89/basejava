package com.urise.model;

import com.urise.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Company {
    private String name;
    private String webSite;
    private List<Period> periodList;


    public Company(String name, String webSite, List<Period> periodList) {
        this.name = name;
        this.webSite = webSite;
        this.periodList = periodList;
    }
    public Company(String name,String webSite, Period...periods) {
        this(name,webSite, Arrays.asList(periods));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setPeriodList(List<Period> periodList) {
        this.periodList = periodList;
    }

    public String getName() {
        return name;
    }

    public String getWebSite() {
        return webSite;
    }

    public List<Period> getPeriodList() {
        return periodList;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", webSite='" + webSite + '\'' +
                ", periodList=" + periodList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) && Objects.equals(webSite, company.webSite) && Objects.equals(periodList, company.periodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, webSite, periodList);
    }

    public static class Period {
        private LocalDate firstDate;
        private LocalDate secondDate;
        private String title;
        private String description;

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this.firstDate = DateUtil.of(startYear, startMonth);
            this.secondDate = DateUtil.of(endYear, endMonth);
            this.description = description;
            this.title = title;
        }

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
}
