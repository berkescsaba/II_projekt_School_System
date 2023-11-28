import java.util.Scanner;

public class Menu {

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
        }
    }

    public static void mainMenu() {
        clearConsole();
        try {
            System.out.println("Üdvözöllek a [FŐMENÜBEN]!");
            System.out.println("Írj be egy számot a kívánt funkció használatához: ");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Diák művelet: [1] - Osztály művelet: [2] - Tanár művelet: [3] - Iskola napló kiíratás: [4] - Kilépés: [5]");
                System.out.print("Funkció száma: ");
                int input = scanner.nextInt();
                if (input == 1) {
                    System.out.println("Diák műveletek");
                    diakMenu();
                    break;
                } else if (input == 2) {
                    System.out.println("Osztály művelet");
                    osztalyMenu();
                    break;
                } else if (input == 3) {
                    System.out.println("Tanár műveletek");
                    tanarMenu();
                    break;
                } else if (input == 4) {
                    Diary.jsonPrint();
                    break;
                } else if (input == 5) {
                    System.out.println("Köszönöm hogy a programot használtad!");
                    break;
                } else {
                    System.out.println("Rossz szám");
                    Menu.mainMenu();
                }
            }
        } catch (Exception e) {
            System.out.println("Csak 1, 2, 3, 4 és 5 közül válszthatsz!!!");
            Menu.mainMenu();
        }
    }

    public static void diakMenu() {
        clearConsole();
        try {
            System.out.println("[DIÁK MENÜ]");
            System.out.println("Írj be egy számot a kívánt funkció használatához: ");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Alap info lekérdezés: [1] - Jegybeírás naplóba: [2] - Új diák felvétele: [3] - Vissza: [4]");
                System.out.print("Funkció száma: ");
                int input = scanner.nextInt();
                if (input == 1) {
                    Student.searchStudent();
                    break;
                } else if (input == 2) {
                    GradeManager.studentGrader();
                    break;
                } else if (input == 3) {
                    Student.studentAdder();
                    break;
                } else if (input == 4) {
                    Menu.mainMenu();
                    break;
                } else {
                    System.out.println("Rossz szám");
                    Menu.diakMenu();
                }
            }
        } catch (Exception e) {
            System.out.println("Hibás bevitel!");
            Menu.diakMenu();
        }
    }

    public static void osztalyMenu() {
        clearConsole();
        try {
            System.out.println("[OSZTÁLY MENÜ]");
            System.out.println("Írj be egy számot a kívánt funkció használatához: ");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Alap info lekérdezés: [1] - Vissza: [2]");
                System.out.print("Funkció száma: ");
                int input = scanner.nextInt();
                if (input == 1) {
                    ClassSearch.searchClass();
                    break;
                } else if (input == 2) {
                    Menu.mainMenu();
                    break;
                } else {
                    System.out.println("Rossz szám");
                    Menu.osztalyMenu();
                }
            }
        } catch (Exception e) {
            System.out.println("Hibás bevitel!");
            Menu.osztalyMenu();
        }
    }

    public static void tanarMenu() {
        clearConsole();
        try {
            System.out.println("[TANÁR MENÜ]");
            System.out.println("Írj be egy számot a kívánt funkció használatához: ");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Tanár kereső: [1] - Új tanár felvétele: [2] - Vissza: [3]");
                System.out.print("Funkció száma: ");
                int input = scanner.nextInt();
                if (input == 1) {
                    Teacher.searchTeacher();
                    break;
                } else if (input == 2) {
                    Teacher.teacherAdder();
                    break;
                } else if (input == 3) {
                    Menu.mainMenu();
                    break;
                } else {
                    System.out.println("Rossz szám");
                    Menu.tanarMenu();
                }
            }
        } catch (Exception e) {
            System.out.println("Hibás bevitel!");
            Menu.tanarMenu();
        }
    }
}
