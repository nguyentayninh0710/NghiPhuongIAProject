package QuizApplication.model;

public class StudentLoginResult {
    private boolean success;
    private String message;
    private Student student;

    public StudentLoginResult(boolean success, String message, Student student) {
        this.success = success;
        this.message = message;
        this.student = student;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Student getStudent() {
        return student;
    }
}