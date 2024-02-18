package com.urise.storage;

import com.urise.model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;

public class ResumeTestData {

    private static final Resume resume1 = AbstractStorageTest.getRESUME_1();
    private static final Resume resume2 = AbstractStorageTest.getRESUME_2();
    private static final Resume resume3 = AbstractStorageTest.getRESUME_3();
    private static final Resume resume4 = AbstractStorageTest.getRESUME_4();

    @Before
    public void setResumes() {
        resume1.addContact(ContactType.MAIL, "PetrovIvan@mail.ru");
        resume1.addContact(ContactType.PHONE, "222333222");
        resume1.addSection(SectionType.OBJECTIVE, new TextSection("Охранник"));
        resume1.addSection(SectionType.PERSONAL, new TextSection("Надежность,Сила,Внимательность"));
        resume1.addSection(SectionType.ACHIEVEMENT, new ListSection("Охрана важных объектов", "Охрана высокопоставленных лиц"));
        resume1.addSection(SectionType.QUALIFICATIONS, new ListSection("Охранник высшей категории", "Телохранитель"));
        resume1.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("ООО Первое охранное агенство", "http://FirstBodyguardAgency.ru",
                                new Company.Period(1987, Month.APRIL, 1999, Month.SEPTEMBER, "Охранник", "Младший сотрудник охраны"),
                                new Company.Period(1999, Month.OCTOBER, 2013, Month.JANUARY, "Старший охранник", "Начальник смены")),

                        new Company("ООО Второе охранное агенство", "http://SecondBodyguardAgency.ru",
                                new Company.Period(2013, Month.JANUARY, 2015, Month.DECEMBER, "Начальник охраны", "Начальник всех смен"),
                                new Company.Period(2016, Month.JANUARY, 2019, Month.JANUARY, "Директор по безопасности", "Решение операционных вопросов подразделения"))));

        resume1.addSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Школа 89", "http://School89.ru",
                                new Company.Period(1967, Month.JANUARY, 1977, Month.SEPTEMBER, "", ""))));


        resume2.addContact(ContactType.MAIL, "GorskayaTanya@mail.ru");
        resume2.addContact(ContactType.PHONE, "444999123");
        resume2.addSection(SectionType.OBJECTIVE, new TextSection("Бухгалтер"));
        resume2.addSection(SectionType.PERSONAL, new TextSection("Математический склад ума,Усидчивость"));
        resume2.addSection(SectionType.ACHIEVEMENT, new ListSection("Ведение бухгалтерского учета по компании", "Закрытие года по холдингу"));
        resume2.addSection(SectionType.QUALIFICATIONS, new ListSection("Курсы по бухгалтерии", "1С"));
        resume2.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("ООО Первое бухгалтерское агенство", "http://FirstBuhAgency.ru",
                                new Company.Period(1987, Month.APRIL, 1999, Month.SEPTEMBER, "Бухгалтер", "Младший бухгалтер"),
                                new Company.Period(1999, Month.OCTOBER, 2013, Month.JANUARY, "Старший бухгалтер", "Руководитель отдела")),

                        new Company("ООО Второе бухгалтерское агенство", "http://SecondBuhAgency.ru",
                                new Company.Period(2013, Month.JANUARY, 2015, Month.DECEMBER, "Старший бухгалтер", "Руководитель отдела"),
                                new Company.Period(2016, Month.JANUARY, 2019, Month.JANUARY, "Главный бухгалтер", "Бухгалтер по холдингу"))));

        resume2.addSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Школа 89", "http://School89.ru",
                                new Company.Period(1967, Month.JANUARY, 1977, Month.SEPTEMBER, "", ""))));

        resume3.addContact(ContactType.MAIL, "RakovEgor@mail.ru");
        resume3.addContact(ContactType.PHONE, "226772345");
        resume3.addSection(SectionType.OBJECTIVE, new TextSection("Программист"));
        resume3.addSection(SectionType.PERSONAL, new TextSection("Математический склад ума,Усидчивость"));
        resume3.addSection(SectionType.ACHIEVEMENT, new ListSection("Разработка ПО для нужд компании", "Исправление ошибок ПО"));
        resume3.addSection(SectionType.QUALIFICATIONS, new ListSection("JAVA", "GIT", "SQL"));
        resume3.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("ООО Первое бухгалтерское агенство", "http://FirstBuhAgency.ru",
                                new Company.Period(1987, Month.APRIL, 1999, Month.SEPTEMBER, "Программист", "Младший программист"),
                                new Company.Period(1999, Month.OCTOBER, 2013, Month.JANUARY, "Программист", "Middle - программист")),

                        new Company("ООО Второе бухгалтерское агенство", "http://SecondBuhAgency.ru",
                                new Company.Period(2013, Month.JANUARY, 2015, Month.DECEMBER, "Программист", "Senior - программист"),
                                new Company.Period(2016, Month.JANUARY, 2019, Month.JANUARY, "Руководитель отряда разработчиков", "Team Lead"))));

        resume3.addSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Школа 89", "http://School89.ru",
                                new Company.Period(1967, Month.JANUARY, 1977, Month.SEPTEMBER, "", ""))));

        resume4.addContact(ContactType.MAIL, "HomichStas@mail.ru");
        resume4.addContact(ContactType.PHONE, "226772345");
        resume4.addSection(SectionType.OBJECTIVE, new TextSection("Программист"));
        resume4.addSection(SectionType.PERSONAL, new TextSection("Математический склад ума,Усидчивость"));
        resume4.addSection(SectionType.ACHIEVEMENT, new ListSection("Разработка ПО для нужд компании", "Исправление ошибок ПО"));
        resume4.addSection(SectionType.QUALIFICATIONS, new ListSection("JAVA", "GIT", "SQL"));
        resume4.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("ООО Первое бухгалтерское агенство", "http://FirstBuhAgency.ru",
                                new Company.Period(1987, Month.APRIL, 1999, Month.SEPTEMBER, "Программист", "Младший программист"),
                                new Company.Period(1999, Month.OCTOBER, 2013, Month.JANUARY, "Программист", "Middle - программист")),

                        new Company("ООО Второе бухгалтерское агенство", "http://SecondBuhAgency.ru",
                                new Company.Period(2013, Month.JANUARY, 2015, Month.DECEMBER, "Программист", "Senior - программист"),
                                new Company.Period(2016, Month.JANUARY, 2019, Month.JANUARY, "Руководитель отряда разработчиков", "Team Lead"))));

        resume4.addSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Школа 89", "http://School89.ru",
                                new Company.Period(1967, Month.JANUARY, 1977, Month.SEPTEMBER, "", ""))));

    }

    @Test
    public void doAllResumes() {
        printResumes(resume1);
        printResumes(resume2);
        printResumes(resume3);
        printResumes(resume4);
    }


    public static void printResumes(Resume resume) {
        System.out.println(resume.getFullName());
        System.out.println(resume.getContacts(ContactType.MAIL));
        System.out.println(resume.getContacts(ContactType.PHONE));
        System.out.println(resume.getSections(SectionType.OBJECTIVE));
        System.out.println(resume.getSections(SectionType.PERSONAL));
        System.out.println(resume.getSections(SectionType.ACHIEVEMENT));
        System.out.println(resume.getSections(SectionType.QUALIFICATIONS));
        System.out.println(resume.getSections(SectionType.EXPERIENCE));
        System.out.println(resume.getSections(SectionType.EDUCATION));


    }

}
