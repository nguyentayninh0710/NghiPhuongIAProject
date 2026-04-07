package QuizApplication.model;

public class StudentQuizReviewItem {
    private final int questionNumber;
    private final String questionId;
    private final String questionContent;
    private final String selectedOption;
    private final String correctOption;
    private final String correctExplanation;
    private final boolean correct;

    public StudentQuizReviewItem(int questionNumber,
                                 String questionId,
                                 String questionContent,
                                 String selectedOption,
                                 String correctOption,
                                 String correctExplanation,
                                 boolean correct) {
        this.questionNumber = questionNumber;
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.selectedOption = selectedOption;
        this.correctOption = correctOption;
        this.correctExplanation = correctExplanation;
        this.correct = correct;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public String getCorrectExplanation() {
        return correctExplanation;
    }

    public boolean isCorrect() {
        return correct;
    }
}