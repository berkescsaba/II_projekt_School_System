import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

public class ClassSearch {
    public static void searchClass() {
        Menu.clearConsole();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Kérlek írd be a keresett osztály számát: ");
        int searchClass = scanner.nextInt();
        String filePath = FilePath.DIARY;
        boolean classFound = false;

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            JsonArray studentsArray = jsonObject.getAsJsonArray("student");
            for (JsonElement studentElement : studentsArray) {
                JsonObject studentObject = studentElement.getAsJsonObject();

                int sclass = studentObject.get("sclass").getAsInt();
                if (sclass == searchClass) {
                    // Diák neve
                    classFound = true;
                    JsonObject nameObject = studentObject.getAsJsonObject("name");
                    System.out.println("Student: " + nameObject.get("firstName").getAsString() + " " +
                            nameObject.get("lastName").getAsString());

                    // Tantárgy, jegyek, átlag kiíratás
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

                    // Osztály, ID és jelenlét kiírása
                    boolean isPresent = studentObject.get("isPresent").getAsBoolean();
                    int id = studentObject.get("id").getAsInt();

                    System.out.println("Osztály: " + sclass + ", ID: " + id + ", Present: " + isPresent);
                    System.out.println();
                }
            }
            Menu.osztalyMenu();
            if (!classFound) {
                System.out.println("A keresett osztályban nincsenek diákok!");
                Menu.osztalyMenu();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
