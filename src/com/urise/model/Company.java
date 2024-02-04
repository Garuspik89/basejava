package com.urise.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {
    private  String name;
    private String webSite;
    private List<Period> periodList;

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

    public Company(String name, String webSite, ArrayList<Period> periodList) {
        this.name = name;
        this.webSite = webSite;
        this.periodList = periodList;
    }

}
