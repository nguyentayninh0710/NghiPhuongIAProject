package QuizApplication.auth;

import QuizApplication.model.LoginResult;

public interface AuthProvider {
    LoginResult loginTeacher(String email, String password);
}
