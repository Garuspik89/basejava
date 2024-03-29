package com.urise.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListSection extends Section implements Serializable {
    private List<String> data;

    public ListSection() {
    }

    public ListSection(List<String> data) {
        this.data = data;
    }

    public ListSection(String... someText) {
        this(Arrays.asList(someText));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    public List<String> getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
