package QuizApplication.dao;

import QuizApplication.config.DBConnection;
import QuizApplication.model.Assignment;
import QuizApplication.model.ClassRoom;
import QuizApplication.model.Quiz;
import QuizApplication.model.QuizTag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

    public List<QuizTag> getAllQuizTags() {
        List<QuizTag> quizTags = new ArrayList<>();

        String sql = "SELECT QuizTagId, QuizTagDescription " +
                     "FROM quiztag " +
                     "ORDER BY QuizTagDescription";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                quizTags.add(new QuizTag(
                        rs.getInt("QuizTagId"),
                        rs.getString("QuizTagDescription")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return quizTags;
    }

    public List<ClassRoom> getClassesByTeacher(String teacherId) {
        List<ClassRoom> classes = new ArrayList<>();

        String sql = "SELECT ClassId, ClassName, TeacherId " +
                     "FROM `class` " +
                     "WHERE TeacherId = ? " +
                     "ORDER BY ClassName";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, teacherId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    classes.add(new ClassRoom(
                            rs.getString("ClassId"),
                            rs.getString("ClassName"),
                            rs.getString("TeacherId")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return classes;
    }

    public boolean existsQuizTitleForTeacher(String quizTitle, String teacherId) {
        String sql = "SELECT COUNT(*) " +
                     "FROM quiz " +
                     "WHERE QuizTitle = ? AND TeacherId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, quizTitle);
            ps.setString(2, teacherId);

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

    public String createQuizAndAssignToClass(Quiz quiz, Assignment assignment) {
        String createdQuizId = null;

        String insertQuizSql =
                "INSERT INTO quiz (QuizId, QuizTitle, Level, QuizTagId, TeacherId) " +
                "VALUES (?, ?, ?, ?, ?)";

        String insertAssignmentSql =
                "INSERT INTO assignment (QuizId, ClassId) " +
                "VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                createdQuizId = generateNextQuizId(conn);

                try (PreparedStatement psQuiz = conn.prepareStatement(insertQuizSql)) {
                    psQuiz.setString(1, createdQuizId);
                    psQuiz.setString(2, quiz.getQuizTitle());
                    psQuiz.setInt(3, quiz.getLevel());
                    psQuiz.setInt(4, quiz.getQuizTagId());
                    psQuiz.setString(5, quiz.getTeacherId());
                    psQuiz.executeUpdate();
                }

                try (PreparedStatement psAssignment = conn.prepareStatement(insertAssignmentSql)) {
                    psAssignment.setString(1, createdQuizId);
                    psAssignment.setString(2, assignment.getClassId());
                    psAssignment.executeUpdate();
                }

                conn.commit();
                return createdQuizId;

            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                return null;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateNextQuizId(Connection conn) throws SQLException {
        String sql =
                "SELECT COALESCE(MAX(CAST(SUBSTRING(QuizId, 2) AS UNSIGNED)), 0) + 1 AS NextNumber " +
                "FROM quiz";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int nextNumber = 1;
            if (rs.next()) {
                nextNumber = rs.getInt("NextNumber");
            }

            return String.format("Q%03d", nextNumber);
        }
    }
}