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
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Student {
    private static void addNewStudent(JSONObject jsonObject, String lastName, String firstName, int sclass, Map<String, List<Integer>> subjectsAndGrades) {
        JSONArray students = (JSONArray) jsonObject.get("student");

        JSONObject newStudent = new JSONObject();
        newStudent.put("id", getNextStudentId(jsonObject));
        JSONObject name = new JSONObject();
        name.put("firstName", firstName);
        name.put("lastName", lastName);
        newStudent.put("name", name);
        newStudent.put("sclass", sclass);

        JSONObject subjectsAndGradeList = new JSONObject();

        for (Map.Entry<String, List<Integer>> entry : subjectsAndGrades.entrySet()) {
            String subject = entry.getKey();
            List<Integer> grades = entry.getValue();
            subjectsAndGradeList.put(subject, grades);
        }

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
    public static void studentAdder() {
        Menu.clearConsole();
        try {
            Scanner scanner = new Scanner(System.in);
            // Meglévő JSON kiolvasása
            JSONObject jsonObject = JsonHandler.jsonReader();

            // Új diák adatainak bekérése
            System.out.print("Írd be a diák keresztnevét: ");
            String lastName = scanner.nextLine();
            System.out.print("Írd be a diák vezetéknevét: ");
            String firstName = scanner.nextLine();
            System.out.print("Melyik osztályba jár: ");
            int sclass = scanner.nextInt();

            // Új tantárgyak és jegyek bekérése
            Map<String, List<Integer>> subjectsAndGrades = new HashMap<>();
            scanner.nextLine();
            while (true) {
                System.out.print("Írj be egy tantárgyat (vagy 'vége' a befejezéshez): ");
                String subject = scanner.nextLine();
                if (subject.equalsIgnoreCase("vége")) {
                    break;
                }
                System.out.print("Írd be az osztályzatokat space-el elválasztva: ");
                String[] gradesStr = scanner.nextLine().split(" ");
                List<Integer> grades = Arrays.stream(gradesStr)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                subjectsAndGrades.put(subject, grades);
            }

            // Új diák hozzáadása a bekért adatokkal
            addNewStudent(jsonObject, lastName, firstName, sclass, subjectsAndGrades);

            // Visszairjuk a módosításokat a JSON file-ba
            JsonHandler.fileWrite(jsonObject);

            System.out.println("Új díák rögzítve!");
            System.out.println("Kilépéshez nyomj egy ENTERT.");
            String exit = scanner.nextLine();
            if (exit.isEmpty()) {
                Menu.diakMenu();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    public static void searchStudent() {
        Menu.clearConsole();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Kérlek írd be a diák kereszt vagy vezetéknevét: ");
        String searchTerm = scanner.nextLine().toLowerCase();
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
                String firstName = nameObject.get("firstName").getAsString().toLowerCase();
                String lastName = nameObject.get("lastName").getAsString().toLowerCase();

                if (firstName.contains(searchTerm) || lastName.contains(searchTerm)) {
                    // Diák neve
                    studentFound = true;
                    System.out.println("Diák: " + nameObject.get("firstName").getAsString() + " " +
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
                    int sclass = studentObject.get("sclass").getAsInt();
                    boolean isPresent = studentObject.get("isPresent").getAsBoolean();
                    String hianyzas;
                    if (isPresent){
                        hianyzas = "Igen";
                    } else {
                        hianyzas = "Nincs";
                    }
                    int id = studentObject.get("id").getAsInt();
                    System.out.println("Osztály: " + sclass + ", ID: " + id + ", Jelen van: " + hianyzas);
                    System.out.println();
                }
            }
            if (!studentFound) {
                System.out.println("A keresett név nem található!");
                Menu.diakMenu();
            }

            System.out.println("Kilépéshez nyomj egy ENTERT.");
            String exit = scanner.nextLine();
            if (exit.isEmpty()) {
                Menu.diakMenu();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
