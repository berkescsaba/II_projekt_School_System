import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GradeManager {

    public static void studentGrader() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FilePath.DIARY));

            JSONObject data = (JSONObject) obj;

            Scanner scanner = new Scanner(System.in);
            System.out.print("Diák vezeték vagy keresztneve: ");
            String searchName = scanner.nextLine();

            JSONArray matchingStudents = findStudentsByPartialName(data, searchName);

            if (!matchingStudents.isEmpty()) {
                System.out.println("Találatok:");
                displayStudentList(matchingStudents);
                System.out.print("Válassz egy ID-t: ");
                int selectedId = scanner.nextInt();

                JSONObject selectedStudent = findStudentById(matchingStudents, selectedId);
                if (selectedStudent != null) {
                    addGradeToSubjects(selectedStudent);
                    updateJsonFile(data, selectedStudent);
                } else {
                    System.out.println("Ilyen ID nem szerepelt a találatok között.");
                    studentGrader();
                }
            } else {
                System.out.println("Nem található ilyen nevű diák.");
                studentGrader();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static JSONArray findStudentsByPartialName(JSONObject data, String partialName) {
        JSONArray students = (JSONArray) data.get("student");
        JSONArray result = new JSONArray();

        for (Object obj : students) {
            JSONObject student = (JSONObject) obj;
            JSONObject studentName = (JSONObject) student.get("name");
            String firstName = (String) studentName.get("firstName");
            String lastName = (String) studentName.get("lastName");

            if (firstName.toLowerCase().contains(partialName.toLowerCase()) ||
                    lastName.toLowerCase().contains(partialName.toLowerCase())) {
                result.add(student);
            }
        }

        return result;
    }

    private static void displayStudentList(JSONArray students) {
        for (Object obj : students) {
            JSONObject student = (JSONObject) obj;
            System.out.println("ID: " + student.get("id") + ", Név: " + studentNameToString((JSONObject) student.get("name")) + ", Osztály: " + student.get("sclass"));
        }
    }

    private static JSONObject findStudentById(JSONArray students, int id) {
        for (Object obj : students) {
            JSONObject student = (JSONObject) obj;
            if ((long) student.get("id") == id) {
                return student;
            }
        }
        return null;
    }

    private static void addGradeToSubjects(JSONObject student) {
        Scanner scanner = new Scanner(System.in);
        JSONObject subjectsAndGradeList = (JSONObject) student.get("subjectsAndGradeList");

        for (Object key : subjectsAndGradeList.keySet()) {
            String subject = (String) key;
            JSONArray grades = (JSONArray) subjectsAndGradeList.get(subject);

            System.out.print("Jegyek beírása " + subject + " tantárgyhoz" + '\n' + "(több jegy esetén üres helyet használj, ENTER-el skippelheted a tantárgyat): ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                List<Integer> newGrades = parseGrades(input);
                grades.addAll(newGrades);
            }
        }
    }

    private static List<Integer> parseGrades(String input) {
        List<Integer> grades = new ArrayList<>();
        String[] gradeStrings = input.split("\\s+");

        for (String gradeString : gradeStrings) {
            try {
                int grade = Integer.parseInt(gradeString);
                grades.add(grade);
            } catch (NumberFormatException e) {
                System.out.println("Hibás jegy tipus: " + gradeString);
            }
        }

        return grades;
    }

//    private static void updateJsonFile(JSONObject data) {
//        try (FileWriter file = new FileWriter(FilePath.DIARY)) {
//            file.write(data.toJSONString());
//            System.out.println("Grades updated successfully." + studentNameToString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static void updateJsonFile(JSONObject data, JSONObject student) {
        try (FileWriter file = new FileWriter(FilePath.DIARY)) {
            file.write(data.toJSONString());
            System.out.println(studentNameToString((JSONObject) student.get("name")) + " jegyei sikeresen frissítve!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String studentNameToString(JSONObject name) {
        String firstName = (String) name.get("firstName");
        String lastName = (String) name.get("lastName");
        return firstName + " " + lastName;
    }
}
