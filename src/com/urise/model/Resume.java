package com.urise.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;
    private String fullName;

    private  Map<ContactEnum, String> contacts = new EnumMap<>(ContactEnum.class);
    private  Map<SectionEnum, Sections> sections = new EnumMap<>(SectionEnum.class);

    public Resume() {

        this(UUID.randomUUID().toString(),"");
    }

    public Resume(String fullName) {
        this.fullName = fullName;
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUuid(){
        return this.uuid;
    }

    public String getFullName() { return this.fullName; }

    public String getContacts(ContactEnum contactEnum) {
        return contacts.get(contactEnum);
    }

    public Sections getSections(SectionEnum sectionEnum) {
        return sections.get(sectionEnum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {

        return uuid.compareTo(o.uuid);
    }
}
