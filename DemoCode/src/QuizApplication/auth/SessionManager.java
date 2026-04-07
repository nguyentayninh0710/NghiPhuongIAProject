package QuizApplication.auth;

import QuizApplication.model.Student;
import QuizApplication.model.Teacher;

public class SessionManager {
    private static Teacher currentTeacher;
    private static Student currentStudent;

    public static void setCurrentTeacher(Teacher teacher) {
        currentTeacher = teacher;
        if (teacher != null) {
            currentStudent = null;
        }
    }

    public static Teacher getCurrentTeacher() {
        return currentTeacher;
    }

    public static void setCurrentStudent(Student student) {
        currentStudent = student;
        if (student != null) {
            currentTeacher = null;
        }
    }

    public static Student getCurrentStudent() {
        return currentStudent;
    }

    public static void clearSession() {
        currentTeacher = null;
        currentStudent = null;
    }

    public static boolean isTeacherLoggedIn() {
        return currentTeacher != null;
    }

    public static boolean isStudentLoggedIn() {
        return currentStudent != null;
    }

    public static boolean isLoggedIn() {
        return currentTeacher != null || currentStudent != null;
    }
}