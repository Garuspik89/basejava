package com.urise.storage;

import com.urise.exception.ExistStorageException;
import com.urise.exception.NotExistStorageException;
import com.urise.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;


public abstract class AbstractStorageTest {

    protected final Storage storage;

    public AbstractStorageTest(Storage typeOfArrayStorage) {
        this.storage = typeOfArrayStorage;
    }

    private static final String UUID_1 = "UUID_1";
    private static final String UUID_2 = "UUID_2";
    private static final String UUID_3 = "UUID_3";
    private static final String UUID_4 = "UUID_4";
    private final String DUMMY = "dummy";
    private static final Resume RESUME_1 = new Resume(UUID_1, "Петров Иван Сергеевич");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Горская Татьяна Алексеевна");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Раков Егор Максимович");
    private static final Resume RESUME_4 = new Resume(UUID_4, "Хомич Станислав Анатольевич");


    static {
        RESUME_1.addContact(ContactType.MAIL, "PetrovIvan@mail.ru");
        RESUME_1.addContact(ContactType.PHONE, "222333222");
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Охранник"));
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Надежность,Сила,Внимательность"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Охрана важных объектов", "Охрана высокопоставленных лиц"));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection("Охранник высшей категории", "Телохранитель"));
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("ООО Первое охранное агенство", "http://FirstBodyguardAgency.ru",
                                new Company.Period(1987, Month.APRIL, 1999, Month.SEPTEMBER, "Охранник", "Младший сотрудник охраны"),
                                new Company.Period(1999, Month.OCTOBER, 2013, Month.JANUARY, "Старший охранник", "Начальник смены")),

                        new Company("ООО Второе охранное агенство", "http://SecondBodyguardAgency.ru",
                                new Company.Period(2013, Month.JANUARY, 2015, Month.DECEMBER, "Начальник охраны", "Начальник всех смен"),
                                new Company.Period(2016, Month.JANUARY, 2019, Month.JANUARY, "Директор по безопасности", "Решение операционных вопросов подразделения"))));

        RESUME_1.addSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Школа 89", "http://School89.ru",
                                new Company.Period(1967, Month.JANUARY, 1977, Month.SEPTEMBER, "", ""))));

        RESUME_2.addContact(ContactType.MAIL, "GorskayaTanya@mail.ru");
        RESUME_2.addContact(ContactType.PHONE, "444999123");
        RESUME_2.addSection(SectionType.OBJECTIVE, new TextSection("Бухгалтер"));
        RESUME_2.addSection(SectionType.PERSONAL, new TextSection("Математический склад ума,Усидчивость"));
        RESUME_2.addSection(SectionType.ACHIEVEMENT, new ListSection("Ведение бухгалтерского учета по компании", "Закрытие года по холдингу"));
        RESUME_2.addSection(SectionType.QUALIFICATIONS, new ListSection("Курсы по бухгалтерии", "1С"));
        RESUME_2.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("ООО Первое бухгалтерское агенство", "http://FirstBuhAgency.ru",
                                new Company.Period(1987, Month.APRIL, 1999, Month.SEPTEMBER, "Бухгалтер", "Младший бухгалтер"),
                                new Company.Period(1999, Month.OCTOBER, 2013, Month.JANUARY, "Старший бухгалтер", "Руководитель отдела")),

                        new Company("ООО Второе бухгалтерское агенство", "http://SecondBuhAgency.ru",
                                new Company.Period(2013, Month.JANUARY, 2015, Month.DECEMBER, "Старший бухгалтер", "Руководитель отдела"),
                                new Company.Period(2016, Month.JANUARY, 2019, Month.JANUARY, "Главный бухгалтер", "Бухгалтер по холдингу"))));

        RESUME_2.addSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Школа 89", "http://School89.ru",
                                new Company.Period(1967, Month.JANUARY, 1977, Month.SEPTEMBER, "", ""))));

        RESUME_3.addContact(ContactType.MAIL, "RakovEgor@mail.ru");
        RESUME_3.addContact(ContactType.PHONE, "226772345");
        RESUME_3.addSection(SectionType.OBJECTIVE, new TextSection("Программист"));
        RESUME_3.addSection(SectionType.PERSONAL, new TextSection("Математический склад ума,Усидчивость"));
        RESUME_3.addSection(SectionType.ACHIEVEMENT, new ListSection("Разработка ПО для нужд компании", "Исправление ошибок ПО"));
        RESUME_3.addSection(SectionType.QUALIFICATIONS, new ListSection("JAVA", "GIT", "SQL"));
        RESUME_3.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("ООО Первое бухгалтерское агенство", "http://FirstBuhAgency.ru",
                                new Company.Period(1987, Month.APRIL, 1999, Month.SEPTEMBER, "Программист", "Младший программист"),
                                new Company.Period(1999, Month.OCTOBER, 2013, Month.JANUARY, "Программист", "Middle - программист")),

                        new Company("ООО Второе бухгалтерское агенство", "http://SecondBuhAgency.ru",
                                new Company.Period(2013, Month.JANUARY, 2015, Month.DECEMBER, "Программист", "Senior - программист"),
                                new Company.Period(2016, Month.JANUARY, 2019, Month.JANUARY, "Руководитель отряда разработчиков", "Team Lead"))));

        RESUME_3.addSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Школа 89", "http://School89.ru",
                                new Company.Period(1967, Month.JANUARY, 1977, Month.SEPTEMBER, "", ""))));

        RESUME_4.addContact(ContactType.MAIL, "HomichStas@mail.ru");
        RESUME_4.addContact(ContactType.PHONE, "226772345");
        RESUME_4.addSection(SectionType.OBJECTIVE, new TextSection("Программист"));
        RESUME_4.addSection(SectionType.PERSONAL, new TextSection("Математический склад ума,Усидчивость"));
        RESUME_4.addSection(SectionType.ACHIEVEMENT, new ListSection("Разработка ПО для нужд компании", "Исправление ошибок ПО"));
        RESUME_4.addSection(SectionType.QUALIFICATIONS, new ListSection("JAVA", "GIT", "SQL"));
        RESUME_4.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("ООО Первое бухгалтерское агенство", "http://FirstBuhAgency.ru",
                                new Company.Period(1987, Month.APRIL, 1999, Month.SEPTEMBER, "Программист", "Младший программист"),
                                new Company.Period(1999, Month.OCTOBER, 2013, Month.JANUARY, "Программист", "Middle - программист")),

                        new Company("ООО Второе бухгалтерское агенство", "http://SecondBuhAgency.ru",
                                new Company.Period(2013, Month.JANUARY, 2015, Month.DECEMBER, "Программист", "Senior - программист"),
                                new Company.Period(2016, Month.JANUARY, 2019, Month.JANUARY, "Руководитель отряда разработчиков", "Team Lead"))));

        RESUME_4.addSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Школа 89", "http://School89.ru",
                                new Company.Period(1967, Month.JANUARY, 1977, Month.SEPTEMBER, "", ""))));
    }

    public static Resume getRESUME_1() {
        return RESUME_1;
    }

    public static Resume getRESUME_2() {
        return RESUME_2;
    }

    public static Resume getRESUME_3() {
        return RESUME_3;
    }

    public static Resume getRESUME_4() {
        return RESUME_4;
    }


    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }


    @Test
    public void update() {
        Resume resumeForUpdating = new Resume(UUID_1, "Геннадий");
        storage.update(resumeForUpdating);
        Assert.assertEquals(resumeForUpdating, storage.get(UUID_1));
    }

 @Test
    public void clear() {
     storage.clear();
     assertSize(0);
     Resume[] resumesAfterClear = storage.getAllSorted().toArray(new Resume[0]);
     Resume[] emptyResumes = new Resume[0];
     Assert.assertArrayEquals(resumesAfterClear, emptyResumes);

 }
    @Test
    public void getAll() {
        Resume[] actual = storage.getAllSorted().toArray(new Resume[1]);
        Resume[] model = new Resume[]{RESUME_2, RESUME_1, RESUME_3};
        Assert.assertArrayEquals(actual, model);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        try {
            assertGet(RESUME_1);
        } catch (NotExistStorageException exception) {
            throw new NotExistStorageException("Sorry, resume doesn't exist");
        }
        Assert.fail();
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test(expected = ExistStorageException.class)
    public void resumeAlreadyExists() {
        storage.save(RESUME_1);
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}
