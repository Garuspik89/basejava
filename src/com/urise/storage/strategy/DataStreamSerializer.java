package com.urise.storage.strategy;

import com.urise.model.*;
import com.urise.util.DateUtil;

import java.io.*;
import java.util.*;

public class DataStreamSerializer implements Converter {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(contacts.size());

            CollectionsWriter contactsWriter = (collection) -> {
                dos.writeUTF(collection.getKey().toString());
                dos.writeUTF((String) collection.getValue());
            };

            writeWithException(contactsWriter, dos, contacts.entrySet());
            dos.writeInt(sections.size());

            CollectionsWriter sectionWriter = (collection) -> {
                SectionType typeOfSection = (SectionType) collection.getKey();
                dos.writeUTF(collection.getKey().toString());
                switch (typeOfSection) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        TextSection obj = (TextSection) collection.getValue();
                        dos.writeUTF(obj.getData());
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        ListSection obj = (ListSection) collection.getValue();
                        List<String> listOfListOfSection = obj.getData();
                        dos.writeInt(listOfListOfSection.size());
                        for (int i = 0; i < listOfListOfSection.size(); i++) {
                            dos.writeUTF(listOfListOfSection.get(i));
                        }
                        break;
                    }
                    case EXPERIENCE:
                    case EDUCATION: {
                        CompanySection companySection = (CompanySection) collection.getValue();
                        List<Company> listOfCompany = companySection.getData();
                        dos.writeInt(listOfCompany.size());
                        for (int i = 0; i < listOfCompany.size(); i++) {
                            dos.writeUTF(listOfCompany.get(i).getName());
                            dos.writeUTF(listOfCompany.get(i).getWebSite());
                            List<Company.Period> listOfPeriodCompany = listOfCompany.get(i).getPeriodList();
                            dos.writeInt(listOfPeriodCompany.size());
                            for (int j = 0; j < listOfPeriodCompany.size(); j++) {
                                dos.writeUTF(listOfPeriodCompany.get(j).getFirstDate().toString());
                                dos.writeUTF(listOfPeriodCompany.get(j).getSecondDate().toString());
                                dos.writeUTF(listOfPeriodCompany.get(j).getDescription());
                                dos.writeUTF(listOfPeriodCompany.get(j).getTitle());
                            }
                        }
                        break;
                    }
                }
            };
            writeWithException(sectionWriter, dos, sections.entrySet());
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int countOfSections = dis.readInt();
            SectionType sectionType;
            for (int i = 0; i < countOfSections; i++) {
                sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        List<String> listOfListSection = new ArrayList<>();
                        int sizeOfListSection = dis.readInt();
                        for (int j = 0; j < sizeOfListSection; j++) {
                            listOfListSection.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(listOfListSection));
                        break;
                    }
                    case EXPERIENCE:
                    case EDUCATION: {
                        List<Company> listOfCompany = new ArrayList<>();
                        List<Company.Period> listOfPeriodCompany = new ArrayList<>();
                        int countOfCompany = dis.readInt();
                        for (int j = 0; j < countOfCompany; j++) {
                            Company company = new Company();
                            company.setName(dis.readUTF());
                            company.setWebSite(dis.readUTF());
                            int countOfPeriodsOfCompany = dis.readInt();
                            for (int k = 0; k < countOfPeriodsOfCompany; k++) {
                                Company.Period companyPeriod = new Company.Period();
                                companyPeriod.setFirstDate(DateUtil.unmarshal(dis.readUTF()));
                                companyPeriod.setSecondDate(DateUtil.unmarshal(dis.readUTF()));
                                companyPeriod.setDescription(dis.readUTF());
                                companyPeriod.setTitle(dis.readUTF());
                                listOfPeriodCompany.add(companyPeriod);
                            }
                            listOfCompany.add(company);
                        }
                        resume.addSection(sectionType, new CompanySection(listOfCompany));
                    }
                }
            }

            return resume;
        }
    }

    private void writeWithException(CollectionsWriter collectionsWriter, DataOutputStream dos, Collection collection) throws IOException {
        Objects.requireNonNull(collectionsWriter);
        Iterator i = collection.iterator();
        while (i.hasNext()) {
            collectionsWriter.writeSomeCollection((Map.Entry) i.next());
        }

    }
}
