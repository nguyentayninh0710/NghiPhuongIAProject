package QuizApplication.model;

public class StudentQuizQuestionItem {
    private final String questionId;
    private final String questionContent;
    private final String optionA;
    private final String optionB;
    private final String optionC;
    private final String optionD;
    private final String correctOption;
    private final String correctExplanation;
    private final int questionMarks;
    private String selectedOption;

    public StudentQuizQuestionItem(String questionId,
                                   String questionContent,
                                   String optionA,
                                   String optionB,
                                   String optionC,
                                   String optionD,
                                   String correctOption,
                                   String correctExplanation,
                                   int questionMarks,
                                   String selectedOption) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
        this.correctExplanation = correctExplanation;
        this.questionMarks = questionMarks;
        this.selectedOption = selectedOption;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public String getCorrectExplanation() {
        return correctExplanation;
    }

    public int getQuestionMarks() {
        return questionMarks;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public boolean isAnswered() {
        return selectedOption != null && !selectedOption.trim().isEmpty();
    }
}