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

            writeWithException(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeWithException(dos, sections.entrySet(), entry -> {
                SectionType typeOfSection = entry.getKey();
                dos.writeUTF(typeOfSection.toString());
                switch (typeOfSection) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        dos.writeUTF(((TextSection) entry.getValue()).getData());
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        ListSection obj = (ListSection) entry.getValue();
                        List<String> listOfListOfSection = obj.getData();
                        writeWithException(dos, listOfListOfSection, dos::writeUTF);
                        break;
                    }
                    case EXPERIENCE:
                    case EDUCATION: {
                        CompanySection companySection = (CompanySection) entry.getValue();
                        List<Company> listOfCompany = companySection.getData();
                        writeWithException(dos, listOfCompany, entryOfListOfCompanies -> {
                            dos.writeUTF(entryOfListOfCompanies.getName());
                            dos.writeUTF(entryOfListOfCompanies.getWebSite());
                            List<Company.Period> listOfPeriodCompany = entryOfListOfCompanies.getPeriodList();
                            writeWithException(dos, listOfPeriodCompany, entryOfListOfPeriods -> {
                                dos.writeUTF(entryOfListOfPeriods.getFirstDate().toString());
                                dos.writeUTF(entryOfListOfPeriods.getSecondDate().toString());
                                dos.writeUTF(entryOfListOfPeriods.getDescription());
                                dos.writeUTF(entryOfListOfPeriods.getTitle());
                            });
                        });
                        break;
                    }
                }
            });
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

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, CollectionsWriter<T> collectionsWriter) throws IOException {
        Objects.requireNonNull(collectionsWriter);
        dos.writeInt(collection.size());
        for (T t : collection) {
            collectionsWriter.writeSomeCollection(t);
        }

    }
}
