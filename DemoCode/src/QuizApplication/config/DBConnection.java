package QuizApplication.config;

import java.sql.*;

public class DBConnection {
	private static final String HOST = "localhost";
	private static final String PORT = "3306";
	private static final String DATABASE = "quiz_application";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "thangbomtn0710";
	
    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=Asia/Ho_Chi_Minh"
            + "&characterEncoding=UTF-8";
    
    private DBConnection() {}
    
    static {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("MySQL JDBC Driver loaded successfully"); 
		} catch (ClassNotFoundException e) {
			System.err.println("Not found MySQL JDBC Driver");
			e.printStackTrace();
		}
    }
    
    public static Connection getConnection() throws SQLException {
    	return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
	
    public static boolean testConnection() {
    	try (Connection conn = getConnection()){
			return conn != null && !conn.isClosed();
		} catch (SQLException e) {
			System.err.println(e.getErrorCode());
			return false;
		}
    }
    
    public static void printConnectionStatus() {
    	if (testConnection()) {
    		System.out.println("Connected");
    	}
    	else {
    		System.out.println("Failed");
    	}
    }
    
    public static void main(String[] args) {
        printConnectionStatus();
    }
}
