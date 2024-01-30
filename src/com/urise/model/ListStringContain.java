package com.urise.model;

import java.util.ArrayList;
import java.util.List;

public class ListStringContain extends Sections {
    private List<String> data = new ArrayList<>();

    public ListStringContain(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }
    @Override
    public String toString() {
        return data.toString();
    }
}
