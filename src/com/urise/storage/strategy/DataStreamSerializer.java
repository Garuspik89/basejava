package com.urise.storage.strategy;

import com.urise.model.*;
import com.urise.util.DateUtil;
import com.urise.util.LocalDateAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Converter {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        dos.writeUTF(entry.getKey().name());
                        TextSection obj = (TextSection) entry.getValue();
                        dos.writeUTF(obj.getData());
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        dos.writeUTF(entry.getKey().name());
                        ListSection obj = (ListSection) entry.getValue();
                        List<String> listOfListOfSection = obj.getData();
                        dos.writeInt(listOfListOfSection.size());
                        listOfListOfSection.forEach(((section) -> {
                            try {
                                dos.writeUTF(section);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }));
                        break;
                    }
                    case EXPERIENCE:
                    case EDUCATION: {
                        dos.writeUTF(entry.getKey().name());
                        CompanySection companySection = (CompanySection) entry.getValue();
                        List<Company> listOfCompany = companySection.getData();
                        dos.writeInt(listOfCompany.size());
                        listOfCompany.forEach((section) -> {
                            try {
                                dos.writeUTF(section.getName());
                                dos.writeUTF(section.getWebSite());
                                List<Company.Period> listOfPeriodCompany = section.getPeriodList();
                                dos.writeInt(listOfPeriodCompany.size());
                                listOfPeriodCompany.forEach((period) -> {
                                    try {
                                        dos.writeUTF(period.getFirstDate().toString());
                                        dos.writeUTF(period.getSecondDate().toString());
                                        dos.writeUTF(period.getDescription());
                                        dos.writeUTF(period.getTitle());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
            }
        }
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
            for (int i = 0; i < countOfSections ;i++) {
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
                        resume.addSection(sectionType,new ListSection(listOfListSection));
                        break;
                    }
                    case EXPERIENCE:
                    case EDUCATION: {
                        List<Company> listOfCompany = new ArrayList<>();
                        List<Company.Period> listOfPeriodCompany = new ArrayList<>();
                        int countOfCompany = dis.readInt();
                        for (int j = 0; j < countOfCompany ; j++) {
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
                        resume.addSection(sectionType,new CompanySection(listOfCompany));
                    }
                }
            }

            return resume;
        }
    }
}
