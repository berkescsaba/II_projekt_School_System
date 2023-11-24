import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WriteToJson {

    private static void addNewTeacher(JSONObject jsonObject, String lastName, String firstName, String subject) {
        JSONArray teachers = (JSONArray) jsonObject.get("teacher");

        JSONObject newTeacher = new JSONObject();
        newTeacher.put("firstName", firstName);
        newTeacher.put("lastName", lastName);
        newTeacher.put("subject", subject);

        teachers.add(newTeacher);
    }

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

    public static void studentAdder() {
        try {
            Scanner scanner = new Scanner(System.in);

            // Meglévő JSON kiolvasása
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(FilePath.DIARY);
            Object obj = parser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) obj;

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
                System.out.print("Írj be egy tantárgyat (vagy 'vége' a befelyezéshez): ");
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
            FileWriter fileWriter = new FileWriter(FilePath.DIARY);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

            System.out.println("Új díák rögzítve!");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
