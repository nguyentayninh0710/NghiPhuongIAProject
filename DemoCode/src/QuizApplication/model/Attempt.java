package QuizApplication.model;

public class Attempt {
    private int attemptId;
    private String dateStart;
    private String timeStart;
    private String dateEnd;
    private String timeEnd;
    private String quizId;
    private String studentId;

    public Attempt() {
    }

    public Attempt(int attemptId, String dateStart, String timeStart, String dateEnd,
                   String timeEnd, String quizId, String studentId) {
        this.attemptId = attemptId;
        this.dateStart = dateStart;
        this.timeStart = timeStart;
        this.dateEnd = dateEnd;
        this.timeEnd = timeEnd;
        this.quizId = quizId;
        this.studentId = studentId;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "attemptId=" + attemptId +
                ", dateStart='" + dateStart + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", quizId='" + quizId + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
