package com.urise.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CompanySection extends Section implements Serializable {

    private List<Company> data;

    public void setData(List<Company> data) {
        this.data = data;
    }

    public CompanySection(List<Company> data) {
        this.data = data;
    }

    public CompanySection(Company... companies) {
        this(Arrays.asList(companies));
    }

    public List<Company> getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
