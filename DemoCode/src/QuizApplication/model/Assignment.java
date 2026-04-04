package QuizApplication.model;

public class Assignment {
    private String quizId;
    private String classId;

    public Assignment() {
    }

    public Assignment(String quizId, String classId) {
        this.quizId = quizId;
        this.classId = classId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "quizId='" + quizId + '\'' +
                ", classId='" + classId + '\'' +
                '}';
    }
}
