package QuizApplication.auth;

import QuizApplication.dao.TeacherDAO;
import QuizApplication.model.LoginResult;
import QuizApplication.model.Teacher;

public class DatabaseAuthProvider implements AuthProvider {

    private TeacherDAO teacherDAO;

    public DatabaseAuthProvider() {
        this.teacherDAO = new TeacherDAO();
    }

    @Override
    public LoginResult loginTeacher(String email, String password) {
        Teacher teacher = teacherDAO.findByEmailAndPassword(email, password);

        if (teacher == null) {
            return new LoginResult(false, "Sai email hoặc password", null);
        }

        return new LoginResult(true, "Đăng nhập thành công", teacher);
    }
}
