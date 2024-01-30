package com.urise.model;

public enum SectionEnum {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
