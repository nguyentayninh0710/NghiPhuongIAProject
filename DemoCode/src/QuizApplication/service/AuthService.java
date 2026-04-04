package QuizApplication.service;

import QuizApplication.auth.AuthProvider;
import QuizApplication.model.LoginResult;

public class AuthService {

    private AuthProvider authProvider;

    public AuthService(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public LoginResult loginTeacher(String email, String password) {
        return authProvider.loginTeacher(email, password);
    }
}
