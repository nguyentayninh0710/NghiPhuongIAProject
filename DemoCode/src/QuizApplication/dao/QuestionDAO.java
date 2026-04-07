package QuizApplication.dao;

import QuizApplication.config.DBConnection;
import QuizApplication.model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public boolean existsQuizById(String quizId) {
        String sql = "SELECT COUNT(*) FROM quiz WHERE QuizId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, quizId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public String insertQuestion(Question question) {
        String insertSql = "INSERT INTO question "
                + "(QuestionId, QuestionContent, OptionA, OptionB, OptionC, OptionD, "
                + "CorrectOption, CorrectExplanation, QuestionMarks, QuizId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            String newQuestionId = generateNextQuestionId(conn);

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, newQuestionId);
                ps.setString(2, question.getQuestionContent());
                ps.setString(3, question.getOptionA());
                ps.setString(4, question.getOptionB());
                ps.setString(5, question.getOptionC());
                ps.setString(6, question.getOptionD());
                ps.setString(7, question.getCorrectOption());
                ps.setString(8, question.getCorrectExplanation());
                ps.setInt(9, question.getQuestionMarks());
                ps.setString(10, question.getQuizId());

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    return newQuestionId;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Question findById(String questionId) {
        String sql = "SELECT QuestionId, QuestionContent, OptionA, OptionB, OptionC, OptionD, "
                + "CorrectOption, CorrectExplanation, QuestionMarks, QuizId "
                + "FROM question WHERE QuestionId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, questionId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Question(
                            rs.getString("QuestionId"),
                            rs.getString("QuestionContent"),
                            rs.getString("OptionA"),
                            rs.getString("OptionB"),
                            rs.getString("OptionC"),
                            rs.getString("OptionD"),
                            rs.getString("CorrectOption"),
                            rs.getString("CorrectExplanation"),
                            rs.getInt("QuestionMarks"),
                            rs.getString("QuizId")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Question> getQuestionsByQuiz(String quizId) {
        List<Question> questions = new ArrayList<>();

        String sql = "SELECT QuestionId, QuestionContent, OptionA, OptionB, OptionC, OptionD, "
                + "CorrectOption, CorrectExplanation, QuestionMarks, QuizId "
                + "FROM question WHERE QuizId = ? "
                + "ORDER BY QuestionId";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, quizId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    questions.add(new Question(
                            rs.getString("QuestionId"),
                            rs.getString("QuestionContent"),
                            rs.getString("OptionA"),
                            rs.getString("OptionB"),
                            rs.getString("OptionC"),
                            rs.getString("OptionD"),
                            rs.getString("CorrectOption"),
                            rs.getString("CorrectExplanation"),
                            rs.getInt("QuestionMarks"),
                            rs.getString("QuizId")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }

    public int countQuestionsByQuiz(String quizId) {
        String sql = "SELECT COUNT(*) FROM question WHERE QuizId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, quizId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean deleteQuestionById(String questionId) {
        String sql = "DELETE FROM question WHERE QuestionId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, questionId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private String generateNextQuestionId(Connection conn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(QuestionId, 3) AS UNSIGNED)), 0) + 1 AS NextNumber "
                + "FROM question";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int nextNumber = 1;
            if (rs.next()) {
                nextNumber = rs.getInt("NextNumber");
            }

            return String.format("QS%03d", nextNumber);
        }
    }
}