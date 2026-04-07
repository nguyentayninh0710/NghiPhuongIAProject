package QuizApplication.model;

public class StudentQuizResultSummary {
    private final int attemptId;
    private final String quizId;
    private final String quizTitle;
    private final int totalQuestions;
    private final int correctAnswers;
    private final int totalMarks;
    private final int earnedMarks;
    private final String scoreText;
    private final String finishedInText;

    public StudentQuizResultSummary(int attemptId,
                                    String quizId,
                                    String quizTitle,
                                    int totalQuestions,
                                    int correctAnswers,
                                    int totalMarks,
                                    int earnedMarks,
                                    String scoreText,
                                    String finishedInText) {
        this.attemptId = attemptId;
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.totalMarks = totalMarks;
        this.earnedMarks = earnedMarks;
        this.scoreText = scoreText;
        this.finishedInText = finishedInText;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public String getQuizId() {
        return quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public int getEarnedMarks() {
        return earnedMarks;
    }

    public String getScoreText() {
        return scoreText;
    }

    public String getFinishedInText() {
        return finishedInText;
    }
}