import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Diary {
    public static void jsonPrint() {
        Menu.clearConsole();
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
                String hianyzas;
                if (isPresent){
                    hianyzas = "Igen";
                } else {
                    hianyzas = "Nincs";
                }
                System.out.println("  Jelen van: " + hianyzas);
                System.out.println();
            }
            System.out.println("Kilépéshez nyomj egy ENTERT.");
            Scanner scanner = new Scanner(System.in);
            String exit = scanner.nextLine();
            if (exit.isEmpty()) {
                Menu.mainMenu();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
