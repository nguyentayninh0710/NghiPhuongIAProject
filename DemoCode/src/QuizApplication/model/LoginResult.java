package QuizApplication.model;

public class LoginResult {
	private boolean success;
	private String message;
	private Teacher teacher;
	public LoginResult(boolean success, String message, Teacher teacher) {
		super();
		this.success = success;
		this.message = message;
		this.teacher = teacher;
	}
	public boolean isSuccess() {
		return success;
	}
	public String getMessage() {
		return message;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	
	
}
