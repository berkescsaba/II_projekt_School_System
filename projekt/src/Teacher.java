import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

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
        Menu.clearConsole();
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
            System.out.println("Kilépéshez nyomj egy ENTERT.");
            String exit = scanner.nextLine();
            if (exit.isEmpty()) {
                Menu.tanarMenu();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void searchTeacher() {
        Menu.clearConsole();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Kérlek írd be a tanár kereszt vagy vezeték, vagy a tantárgy nevét: ");
        String searchTerm = scanner.nextLine().toLowerCase().trim();
        String filePath = FilePath.DIARY;
        boolean teacherFound = false;

        try {
            String jsonString = Files.readString(Paths.get(filePath));
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            JsonArray teachersArray = jsonObject.getAsJsonArray("teacher");

            for (JsonElement teacherElement : teachersArray) {
                JsonObject teacherObject = teacherElement.getAsJsonObject();
                String firstName = teacherObject.get("firstName").getAsString().toLowerCase();
                String lastName = teacherObject.get("lastName").getAsString().toLowerCase();
                String subject = teacherObject.get("subject").getAsString().toLowerCase();

                if (firstName.contains(searchTerm) || lastName.contains(searchTerm) || subject.contains(searchTerm)) {
                    // tanár kiiratása
                    teacherFound = true;
                    System.out.println("Tanár: " + teacherObject.get("firstName") + " " +
                            teacherObject.get("lastName") + ", Tantárgy: " + teacherObject.get("subject"));
                }
            }

            if (!teacherFound) {
                System.out.println("A tanár nem található!");
            }
            System.out.println("Kilépéshez nyomj egy ENTERT.");
            String exit = scanner.nextLine();
            if (exit.isEmpty()) {
                Menu.tanarMenu();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
