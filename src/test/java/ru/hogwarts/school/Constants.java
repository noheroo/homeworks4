package ru.hogwarts.school;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.record.FacultyRecord;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final Long ID1= 101L;
    public static final String NAME1 = "testName1";
    public static final String COLOR1 = "testColor1";

    public static final Long ID21= 102L;
    public static final Long ID22= 103L;
    public static final String NAME2 = "testName2";

    public static final String NAME3 = "testName3";
    public static final String COLOR3 = "testColor3";

    public static final Faculty TEST_FACULTY_1 = new Faculty(ID1,NAME1,COLOR1);
    public static final Faculty TEST_FACULTY_21 = new Faculty(ID21,NAME2,COLOR1);
    public static final Faculty TEST_FACULTY_22 = new Faculty(ID22,NAME2,COLOR1);
    public static final Faculty TEST_FACULTY_3 = new Faculty(ID1,NAME3,COLOR3);

    public static final List<Faculty> TEST_LIST = new ArrayList<>(List.of(TEST_FACULTY_21, TEST_FACULTY_22));
}
