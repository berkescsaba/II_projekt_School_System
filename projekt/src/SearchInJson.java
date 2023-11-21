
import java.io.IOException;
import java.text.DecimalFormat;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class SearchInJson {
    public static void jsonPrint() {

        try {
            // Tartalom beolvasása JSON-bol
            String jsonString = new String(Files.readAllBytes(Paths.get(FilePath.DIARY)));

            // Gson létrehozása
            Gson gson = new Gson();

            // JSON string átadása a JsonObject-be
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            JsonArray teachersArray = jsonObject.getAsJsonArray("teacher");
            for (JsonElement teacherElement : teachersArray) {
                JsonObject teacherObject = teacherElement.getAsJsonObject();

                String firstName = teacherObject.get("firstName").getAsString();
                String lastName = teacherObject.get("lastName").getAsString();
                String subject = teacherObject.get("subejct").getAsString();

                // teacher kiíratása
                System.out.println("Teacher: " + firstName + " " + lastName + ", Subject: " + subject);
            }

            // student Array feldolgozás
            JsonArray studentsArray = jsonObject.getAsJsonArray("student");
            for (JsonElement studentElement : studentsArray) {
                JsonObject studentObject = studentElement.getAsJsonObject();

                int studentId = studentObject.get("id").getAsInt();

                JsonObject nameObject = studentObject.getAsJsonObject("name");
                String firstName = nameObject.get("firstName").getAsString();
                String lastName = nameObject.get("lastName").getAsString();
                int sclass = studentObject.get("sclass").getAsInt();

                JsonObject subjectsAndGradeListObject = studentObject.getAsJsonObject("subjectsAndGradeList");

                // student adatok kiíratása
                System.out.println("Student ID: " + studentId + ", Name: " + firstName + " " + lastName + ", Class: " + sclass);

                // subjects és grades kiíratás és átlag számolás
                for (String subject : subjectsAndGradeListObject.keySet()) {
                    JsonArray gradesArray = subjectsAndGradeListObject.getAsJsonArray(subject);

                    // Átlag számítás
                    float sum = 0;
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    for (JsonElement gradeElement : gradesArray) {
                        sum += gradeElement.getAsInt();
                    }
                    float average = sum / gradesArray.size();
                    System.out.println("  Subject: " + subject + ", Grades: " + gradesArray.toString() + ", Average: " + df.format(average));
                }
                boolean isPresent = studentObject.get("isPresent").getAsBoolean();
                System.out.println("  Present: " + isPresent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void searchTeacher(String searchTerm) {
        String filePath = "projekt/src/diary.json";
        String searchName = searchTerm;

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            JsonArray teachersArray = jsonObject.getAsJsonArray("teacher");
            for (JsonElement teacherElement : teachersArray) {
                JsonObject teacherObject = teacherElement.getAsJsonObject();
                String firstName = teacherObject.get("firstName").getAsString();
                String lastName = teacherObject.get("lastName").getAsString();
                String subject = teacherObject.get("subject").getAsString();
                if (firstName.equals(searchName) || lastName.equals(searchName)) {
                    //  kiíratása
                    System.out.println("Teacher: " + firstName + " " + lastName + ", Subject: " + subject);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
