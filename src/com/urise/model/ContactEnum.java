package com.urise.model;

public enum ContactEnum {
    PHONE("Тел."),
    SKYPE("Skype"),
    MAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STATCKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    ContactEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
