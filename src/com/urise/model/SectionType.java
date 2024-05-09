package com.urise.model;

public enum SectionType {
    PERSONAL("Личные данные") {
        @Override
        public String toHtml0(String value) {
            return getTitle()  + value;
        }
    },
    OBJECTIVE("Позиция") {
        @Override
        public String toHtml0(String value) {
            return value;
        }
    },
    ACHIEVEMENT("Достижения") {
        @Override
        public String toHtml0(String value) {
            return value;
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtml0(String value) {
            return value;
        }
    },
    EXPERIENCE("Опыт") {
        @Override
        public String toHtml0(String value) {
            return value;
        }
    },

    EDUCATION("Образование") {
        @Override
        public String toHtml0(String value) {
            return value;
        }
    };

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
