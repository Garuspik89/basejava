package com.urise.model;

import java.util.ArrayList;
import java.util.List;

public class ListDataContain extends Sections{
    List<DataContain> data = new ArrayList<>();

    public ListDataContain(List<DataContain> data) {
        this.data = data;
    }

    public List<DataContain> getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
