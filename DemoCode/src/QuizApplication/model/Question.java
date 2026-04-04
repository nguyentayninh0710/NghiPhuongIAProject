package QuizApplication.model;

public class Question {
    private String questionId;
    private String questionContent;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;
    private String correctExplanation;
    private int questionMarks;
    private String quizId;

    public Question() {
    }

    public Question(String questionId, String questionContent, String optionA, String optionB,
                    String optionC, String optionD, String correctOption,
                    String correctExplanation, int questionMarks, String quizId) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
        this.correctExplanation = correctExplanation;
        this.questionMarks = questionMarks;
        this.quizId = quizId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public String getCorrectExplanation() {
        return correctExplanation;
    }

    public void setCorrectExplanation(String correctExplanation) {
        this.correctExplanation = correctExplanation;
    }

    public int getQuestionMarks() {
        return questionMarks;
    }

    public void setQuestionMarks(int questionMarks) {
        this.questionMarks = questionMarks;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId='" + questionId + '\'' +
                ", questionContent='" + questionContent + '\'' +
                ", correctOption='" + correctOption + '\'' +
                ", questionMarks=" + questionMarks +
                ", quizId='" + quizId + '\'' +
                '}';
    }
}

