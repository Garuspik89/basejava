package com.urise.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    List<Company> data = new ArrayList<>();

    public CompanySection(List<Company> data) {
        this.data = data;
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
