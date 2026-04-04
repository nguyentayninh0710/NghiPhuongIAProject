package QuizApplication.model;

public class ClassRoom {
    private String classId;
    private String className;
    private String teacherId;

    public ClassRoom() {
    }

    public ClassRoom(String classId, String className, String teacherId) {
        this.classId = classId;
        this.className = className;
        this.teacherId = teacherId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }
}
