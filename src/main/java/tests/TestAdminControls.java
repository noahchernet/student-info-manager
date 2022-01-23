package tests;

import com.student_info_manager.control.*;
import com.student_info_manager.models.*;
import org.junit.*;

import static junit.framework.TestCase.*;
import static org.junit.Assert.*;

/**
 * Tests the adding and removing methods of Admin
 */
public class TestAdminControls {
    Admin admin;
    Login login;

    @Before
    public void initiate() {
        login = new Login();
        admin = login.connectAdmin("mekonin_g", "3break343");
    }

    @Test
    public void testAddNewTeacher() {
        Teacher newTeacherToAdd = new Teacher("Melaku", "Kebede", "Gemechis", "Mechanics I: Statics", "C,D,E",
                2018,"Mechanical Engineering");
        admin.addTeacher(newTeacherToAdd, "melaku_k", "garamond_serif78");

        // To re-establish connection with the database. Removing this line will throw a database
        // not found SQL exception
        login = new Login();
        Teacher newTeacherFromDatabase = login.connectTeacher("melaku_k", "garamond_serif78");

        assertArrayEquals(newTeacherToAdd.getName(), newTeacherFromDatabase.getName());
        assertEquals(newTeacherToAdd.getSectionsTeaching(), newTeacherFromDatabase.getSectionsTeaching());
        assertEquals(newTeacherToAdd.getCourseTeaching(), newTeacherFromDatabase.getCourseTeaching());
        assertEquals(newTeacherToAdd.getBatchTeaching(), newTeacherFromDatabase.getBatchTeaching());
        assertEquals(newTeacherToAdd.getDepartment(), newTeacherFromDatabase.getDepartment());

        // Delete teacher to ensure when the test is rerun, melaku_k is not in the database
        admin.removeTeacher("melaku_k");
    }

    @Test
    public void testAddNewStudent() {
        Student newStudentToAdd = new Student("Melaku", "Kebede", "Gemechis", "A", 2019, "Food Science",
                "ETS0856/11");
        admin.addStudent(newStudentToAdd, "garamond_serif78");

        // To re-establish connection with the database. Removing this line will throw a database
        // not found SQL exception
        login = new Login();
        Student newStudentFromDatabase = login.connectStudent(newStudentToAdd.getID(), "garamond_serif78");

        assertArrayEquals(newStudentToAdd.getName(), newStudentFromDatabase.getName());
        assertEquals(newStudentToAdd.getDepartment(), newStudentFromDatabase.getDepartment());
        assertEquals(newStudentToAdd.getBatch(), newStudentFromDatabase.getBatch());
        assertEquals(newStudentToAdd.getSection(), newStudentFromDatabase.getSection());
        assertEquals(newStudentToAdd.getID(), newStudentFromDatabase.getID());

        // Delete teacher to ensure when the test is rerun, melaku_k is not in the database
        admin.removeStudent(newStudentToAdd.getID());
    }

    @Test
    public void testAddCourse() {
        Course courseToAdd = new Course("Soil Mechanics", 3, 5,
                "Mechanics I: Statics", "Civil Engineering");
        admin.addCourse(courseToAdd);

        Course courseFromDatabase = admin.getCourse("Soil Mechanics");
        assertEquals(courseToAdd.getTitle(), courseFromDatabase.getTitle());
        assertEquals(courseToAdd.getCredit_hour(), courseFromDatabase.getCredit_hour());
        assertEquals(courseToAdd.getSemester_given(), courseFromDatabase.getSemester_given());
        assertEquals(courseToAdd.getPrerequisite(), courseFromDatabase.getPrerequisite());

        admin.removeCourse(courseToAdd.getTitle());
    }
}
