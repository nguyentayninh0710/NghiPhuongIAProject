package QuizApplication.service;

import java.util.regex.Pattern;

import QuizApplication.dao.TeacherDAO;
import QuizApplication.model.LoginResult;
import QuizApplication.model.Teacher;

public class TeacherService {

    private final TeacherDAO teacherDAO;

    public TeacherService() {
        this.teacherDAO = new TeacherDAO();
    }

    public LoginResult loginTeacher(String email, String password) {
        String normalizedEmail = normalizeEmail(email);
        String normalizedPassword = normalizePassword(password);

        // Validate email
        if (normalizedEmail.isEmpty()) {
            return new LoginResult(false, "Email not empty", null);
        }

        if (!isValidEmail(normalizedEmail)) {
            return new LoginResult(false, "Email invalid", null);
        }

        // Validate password
        if (normalizedPassword.isEmpty()) {
            return new LoginResult(false, "Password not empty", null);
        }

        // Gọi DAO để kiểm tra DB
        Teacher teacher = teacherDAO.findByEmailAndPassword(normalizedEmail, normalizedPassword);

        if (teacher == null) {
            return new LoginResult(false, "Incorrect email or password!", null);
        }

        return new LoginResult(true, "Login successfully", teacher);
    }

    private String normalizeEmail(String email) {
        return email == null ? "" : email.trim();
    }

    private String normalizePassword(String password) {
        return password == null ? "" : password.trim();
    }

    private boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email);
    }
}
