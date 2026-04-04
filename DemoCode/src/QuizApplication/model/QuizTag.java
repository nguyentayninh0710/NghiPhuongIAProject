package QuizApplication.model;

public class QuizTag {
    private int quizTagId;
    private String quizTagDescription;

    public QuizTag() {
    }

    public QuizTag(int quizTagId, String quizTagDescription) {
        this.quizTagId = quizTagId;
        this.quizTagDescription = quizTagDescription;
    }

    public int getQuizTagId() {
        return quizTagId;
    }

    public void setQuizTagId(int quizTagId) {
        this.quizTagId = quizTagId;
    }

    public String getQuizTagDescription() {
        return quizTagDescription;
    }

    public void setQuizTagDescription(String quizTagDescription) {
        this.quizTagDescription = quizTagDescription;
    }

    @Override
    public String toString() {
        return "QuizTag{" +
                "quizTagId=" + quizTagId +
                ", quizTagDescription='" + quizTagDescription + '\'' +
                '}';
    }
}
