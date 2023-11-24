import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Teacher {
    private static void addNewTeacher(JSONObject jsonObject, String lastName, String firstName, String subject) {
        JSONArray teachers = (JSONArray) jsonObject.get("teacher");

        JSONObject newTeacher = new JSONObject();
        newTeacher.put("firstName", firstName);
        newTeacher.put("lastName", lastName);
        newTeacher.put("subject", subject);

        teachers.add(newTeacher);
    }
    public static void teacherAdder() {
        Scanner scanner = new Scanner(System.in);
        try {
            // Meglévő JSON kiolvasása
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(FilePath.DIARY);
            Object obj = parser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) obj;

            // Tanár adatok bekérése
            System.out.print("Írd be a diák keresztnevét: ");
            String lastName = scanner.nextLine();
            System.out.print("Írd be a diák vezetéknevét: ");
            String firstName = scanner.nextLine();
            System.out.print("Melyik osztályba jár: ");
            String subject = scanner.nextLine();

            // Új tanár hozzáadása
            addNewTeacher(jsonObject, lastName, firstName, subject);


            // Visszairjuk a módosításokat a JSON file-ba
            FileWriter fileWriter = new FileWriter(FilePath.DIARY);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

            System.out.println("Új Tanár hozzáadva!");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

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
}
