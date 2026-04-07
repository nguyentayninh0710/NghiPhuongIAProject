package QuizApplication.service;

import QuizApplication.dao.QuestionDAO;
import QuizApplication.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionService {

    private final QuestionDAO questionDAO;

    public QuestionService() {
        this.questionDAO = new QuestionDAO();
    }

    public String addQuestionToQuiz(String quizId,
                                    String questionContent,
                                    String optionA,
                                    String optionB,
                                    String optionC,
                                    String optionD,
                                    String correctOption,
                                    String correctExplanation) {

        String normalizedQuizId = safeTrim(quizId);
        String normalizedContent = safeTrim(questionContent);
        String normalizedOptionA = safeTrim(optionA);
        String normalizedOptionB = safeTrim(optionB);
        String normalizedOptionC = safeTrim(optionC);
        String normalizedOptionD = safeTrim(optionD);
        String normalizedCorrectOption = safeTrim(correctOption).toUpperCase();
        String normalizedExplanation = safeTrim(correctExplanation);

        if (normalizedQuizId.isEmpty()) {
            throw new IllegalArgumentException("Quiz ID is missing.");
        }

        if (!questionDAO.existsQuizById(normalizedQuizId)) {
            throw new IllegalArgumentException("Quiz does not exist.");
        }

        if (normalizedContent.isEmpty()) {
            throw new IllegalArgumentException("Question content must not be empty.");
        }

        if (normalizedOptionA.isEmpty() || normalizedOptionB.isEmpty()
                || normalizedOptionC.isEmpty() || normalizedOptionD.isEmpty()) {
            throw new IllegalArgumentException("All answer options must be filled.");
        }

        if (!isValidCorrectOption(normalizedCorrectOption)) {
            throw new IllegalArgumentException("Correct option must be A, B, C, or D.");
        }

        if (normalizedExplanation.isEmpty()) {
            throw new IllegalArgumentException("Correct explanation must not be empty.");
        }

        Question question = new Question();
        question.setQuizId(normalizedQuizId);
        question.setQuestionContent(normalizedContent);
        question.setOptionA(normalizedOptionA);
        question.setOptionB(normalizedOptionB);
        question.setOptionC(normalizedOptionC);
        question.setOptionD(normalizedOptionD);
        question.setCorrectOption(normalizedCorrectOption);
        question.setCorrectExplanation(normalizedExplanation);
        question.setQuestionMarks(1);

        String createdQuestionId = questionDAO.insertQuestion(question);

        if (createdQuestionId == null || createdQuestionId.trim().isEmpty()) {
            throw new IllegalStateException("Failed to save question to database.");
        }

        return createdQuestionId;
    }

    public Question findById(String questionId) {
        String normalizedQuestionId = safeTrim(questionId);
        if (normalizedQuestionId.isEmpty()) {
            return null;
        }
        return questionDAO.findById(normalizedQuestionId);
    }

    public List<Question> getQuestionsByQuiz(String quizId) {
        String normalizedQuizId = safeTrim(quizId);
        if (normalizedQuizId.isEmpty()) {
            return new ArrayList<>();
        }
        return questionDAO.getQuestionsByQuiz(normalizedQuizId);
    }

    public int countQuestionsByQuiz(String quizId) {
        String normalizedQuizId = safeTrim(quizId);
        if (normalizedQuizId.isEmpty()) {
            return 0;
        }
        return questionDAO.countQuestionsByQuiz(normalizedQuizId);
    }

    public boolean deleteQuestionById(String questionId) {
        String normalizedQuestionId = safeTrim(questionId);
        if (normalizedQuestionId.isEmpty()) {
            return false;
        }
        return questionDAO.deleteQuestionById(normalizedQuestionId);
    }

    private boolean isValidCorrectOption(String correctOption) {
        return "A".equals(correctOption)
                || "B".equals(correctOption)
                || "C".equals(correctOption)
                || "D".equals(correctOption);
    }

    private String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }
}