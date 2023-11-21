import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToJson {

    public static void work() {
        try {
            // Meglévő JSON kiolvasása
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(FilePath.DIARY);
            Object obj = parser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) obj;

            // Új tanár hozzáadása
            addNewTeacher(jsonObject, "Kovács", "Mária", "BIOLOGY");
            // Új tanár hozzáadása
            addNewStudent(jsonObject, "Nagy", "Péter", 10, "CHEMISTRY");

            // Visszairjuk a módosításokat a JSON file-ba
            FileWriter fileWriter = new FileWriter(FilePath.DIARY);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

            System.out.println("New teacher and student added successfully!");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void addNewTeacher(JSONObject jsonObject, String lastName, String firstName, String subject) {
        JSONArray teachers = (JSONArray) jsonObject.get("teacher");

        JSONObject newTeacher = new JSONObject();
        newTeacher.put("firstName", firstName);
        newTeacher.put("lastName", lastName);
        newTeacher.put("subject", subject);

        teachers.add(newTeacher);
    }

    private static void addNewStudent(JSONObject jsonObject, String lastName, String firstName, int sclass, String subject) {
        JSONArray students = (JSONArray) jsonObject.get("student");

        JSONObject newStudent = new JSONObject();
        newStudent.put("id", getNextStudentId(jsonObject));
        JSONObject name = new JSONObject();
        name.put("firstName", firstName);
        name.put("lastName", lastName);
        newStudent.put("name", name);
        newStudent.put("sclass", sclass);

        JSONObject subjectsAndGradeList = new JSONObject();
        subjectsAndGradeList.put(subject, new JSONArray());
        newStudent.put("subjectsAndGradeList", subjectsAndGradeList);

        newStudent.put("isPresent", true);

        students.add(newStudent);
    }

    private static long getNextStudentId(JSONObject jsonObject) {
        JSONArray students = (JSONArray) jsonObject.get("student");
        if (students.isEmpty()) {
            return 1;
        } else {
            JSONObject lastStudent = (JSONObject) students.get(students.size() - 1);
            return (long) lastStudent.get("id") + 1;
        }
    }
}
