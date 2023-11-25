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
            JSONObject jsonObject = JsonHandler.jsonReader();

            // Tanár adatok bekérése
            System.out.print("Írd be a tanár keresztnevét: ");
            String lastName = scanner.nextLine();
            System.out.print("Írd be a tanár vezetéknevét: ");
            String firstName = scanner.nextLine();
            System.out.print("Mit tanít (max 1): ");
            String subject = scanner.nextLine();

            // Új tanár hozzáadása
            addNewTeacher(jsonObject, lastName, firstName, subject);

            // Visszairjuk a módosításokat a JSON file-ba
            JsonHandler.fileWrite(jsonObject);

            System.out.println("Új Tanár hozzáadva!");
            Menu.mainMenu();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }



    public static void searchTeacher() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Kérlek írd be a tanár kereszt vagy vezeték, vagy a tantárgy nevét : ");
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
                if (firstName.toLowerCase().contains(searchTerm) || lastName.toLowerCase().contains(searchTerm) || subject.toLowerCase().contains(searchTerm)) {
                    //  kiíratása
                    teacherFound = true;
                    System.out.println("Tanár: " + firstName + " " + lastName + ", Tantárgy: " + subject);
                    Menu.mainMenu();
                }
            }
            if (!teacherFound) {
                System.out.println("A tanár nem található!");
                searchTeacher();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
