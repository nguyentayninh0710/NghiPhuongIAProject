package QuizApplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import QuizApplication.config.DBConnection;
import QuizApplication.model.Teacher;

public class TeacherDAO {
	public Teacher findByEmailAndPassword(String email, String password) {
		String sql = "SELECT TeacherId, TeacherName, TeacherEmail "
					+ "FROM teacher WHERE TeacherEmail = ? AND TeacherPassword = ?"; 
		try (Connection conn = DBConnection.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Teacher( rs.getString("TeacherId"), 
									rs.getString("TeacherName"), 
									rs.getString("TeacherEmail")
								);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 public static class TeacherClassInfo {
	        private String classId;
	        private String className;

	        public TeacherClassInfo(String classId, String className) {
	            this.classId = classId;
	            this.className = className;
	        }

	        public String getClassId() {
	            return classId;
	        }

	        public String getClassName() {
	            return className;
	        }
	 }
	 public static class TeacherQuizInfo {
	        private String quizId;
	        private String quizTitle;
	        private int level;

	        public TeacherQuizInfo(String quizId, String quizTitle, int level) {
	            this.quizId = quizId;
	            this.quizTitle = quizTitle;
	            this.level = level;
	        }

	        public String getQuizId() {
	            return quizId;
	        }

	        public String getQuizTitle() {
	            return quizTitle;
	        }

	        public int getLevel() {
	            return level;
	        }
	    }
	 
	 public List<TeacherClassInfo> getClassesByTeacher(String teacherId) {
	        List<TeacherClassInfo> classes = new ArrayList<>();

	        String sql = "SELECT ClassId, ClassName "
	                   + "FROM `class` "
	                   + "WHERE TeacherId = ?";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, teacherId);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                classes.add(new TeacherClassInfo(
	                    rs.getString("ClassId"),
	                    rs.getString("ClassName")
	                ));
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return classes;
	    }
	 public List<TeacherQuizInfo> getQuizzesByTeacher(String teacherId) {
	        List<TeacherQuizInfo> quizzes = new ArrayList<>();

	        String sql = "SELECT QuizId, QuizTitle, Level "
	                   + "FROM quiz "
	                   + "WHERE TeacherId = ?";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, teacherId);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                quizzes.add(new TeacherQuizInfo(
	                    rs.getString("QuizId"),
	                    rs.getString("QuizTitle"),
	                    rs.getInt("Level")
	                ));
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return quizzes;
	    }
}
