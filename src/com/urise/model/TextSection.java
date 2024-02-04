package com.urise.model;

import java.util.Objects;

public class TextSection extends Section {
    private String data;

    public TextSection(String data){
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "data='" + data + '\'' +
                '}';
    }

    public String getData(){
        return data;
    }
}
