package QuizApplication.auth;

import QuizApplication.model.Teacher;

public class SessionManager {
    private static Teacher currentTeacher;

    public static void setCurrentTeacher(Teacher teacher) {
        currentTeacher = teacher;
    }

    public static Teacher getCurrentTeacher() {
        return currentTeacher;
    }

    public static void clearSession() {
        currentTeacher = null;
    }

    public static boolean isLoggedIn() {
        return currentTeacher != null;
    }
}
