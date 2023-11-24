
import java.io.IOException;
import java.text.DecimalFormat;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public class SearchInJson {
    /**
     * Minden adatok kiír ami a DIARY JSON-ben található, formázva.
     */
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
                String subject = teacherObject.get("subject").getAsString();

                // teacher kiíratása
                System.out.println("Tanár: " + firstName + " " + lastName + ", Tantárgy: " + subject);
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
                System.out.println("Diák ID: " + studentId + ", Neve: " + firstName + " " + lastName + ", Osztály: " + sclass);

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
                    System.out.println("  Tantárgy: " + subject + ", Jegyek: " + gradesArray + ", Átlag: " + df.format(average));
                }
                boolean isPresent = studentObject.get("isPresent").getAsBoolean();
                System.out.println("  Jelen van: " + isPresent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tanár nevére keres rá, kiírja mit tanít.
     *
     * @param searchTerm
     */

    public static void searchTeacher() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Kérlek írd be a tanár kereszt vagy vezetéknevét: ");
        String searchTerm = scanner.nextLine();
        String filePath = FilePath.DIARY;
        boolean teacherFound = false;
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
                if (firstName.equals(searchTerm) || lastName.equals(searchTerm)) {
                    //  kiíratása
                    teacherFound = true;
                    System.out.println("Teacher: " + firstName + " " + lastName + ", Subject: " + subject);
                }
            }
            if (!teacherFound) {
                System.out.println("A keresett név nem található!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void searchStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Kérlek írd be a diák kereszt vagy vezetéknevét: ");
        String searchTerm = scanner.nextLine();
        String filePath = FilePath.DIARY;
        boolean studentFound = false;

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            JsonArray studentsArray = jsonObject.getAsJsonArray("student");
            for (JsonElement studentElement : studentsArray) {
                JsonObject studentObject = studentElement.getAsJsonObject();
                JsonObject nameObject = studentObject.getAsJsonObject("name");
                String firstName = nameObject.get("firstName").getAsString();
                String lastName = nameObject.get("lastName").getAsString();

                if (firstName.equals(searchTerm) || lastName.equals(searchTerm)) {
                    // Print student details
                    studentFound = true;
                    System.out.println("Student: " + firstName + " " + lastName);

                    // Print subjects and grades
                    JsonObject subjectsAndGradeList = studentObject.getAsJsonObject("subjectsAndGradeList");
                    for (Map.Entry<String, JsonElement> entry : subjectsAndGradeList.entrySet()) {
                        String subject = entry.getKey();
                        JsonArray gradesArray = entry.getValue().getAsJsonArray();
                        float sum = 0;
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                        for (JsonElement gradeElement : gradesArray) {
                            sum += gradeElement.getAsInt();
                        }
                        float average = sum / gradesArray.size();
                        System.out.println("  Tantárgy: " + subject + ", Jegyek: " + gradesArray + ", Átlag: " + df.format(average));
                    }

                    // Print other details like class, id, and presence
                    int sclass = studentObject.get("sclass").getAsInt();
                    boolean isPresent = studentObject.get("isPresent").getAsBoolean();
                    int id = studentObject.get("id").getAsInt();

                    System.out.println("Osztály: " + sclass + ", ID: " + id + ", Present: " + isPresent);
                }
            }

            if (!studentFound) {
                System.out.println("A keresett név nem található!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
