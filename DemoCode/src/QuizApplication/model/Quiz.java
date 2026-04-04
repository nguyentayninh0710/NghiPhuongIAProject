package QuizApplication.model;

public class Quiz {
    private String quizId;
    private String quizTitle;
    private int level;
    private int quizTagId;
    private String teacherId;

    public Quiz() {
    }

    public Quiz(String quizId, String quizTitle, int level, int quizTagId, String teacherId) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.level = level;
        this.quizTagId = quizTagId;
        this.teacherId = teacherId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getQuizTagId() {
        return quizTagId;
    }

    public void setQuizTagId(int quizTagId) {
        this.quizTagId = quizTagId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId='" + quizId + '\'' +
                ", quizTitle='" + quizTitle + '\'' +
                ", level=" + level +
                ", quizTagId=" + quizTagId +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }
}
