import java.util.Scanner;

public class MenuSelect {

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
            System.out.println("Üdvözöllek!");
            System.out.println("Írj be egy számot a kívánt funkció használatához: ");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Diák művelet: 1");
                System.out.println("Osztály művelet: 2");
                System.out.println("Tanár művelet: 3");
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
                    System.out.println("Tanár kereső");
                    SearchInJson.searchTeacher();
                    break;
                } else {
                    System.out.println("Rossz szám");
                }
            }
        } catch (Exception e) {
            System.out.println("Csak 1, 2 és 3 közül válszthatsz!!!");
        }
    }

    public static void diakMenu() {
        clearConsole();
        try {
            System.out.println("Írj be egy számot a kívánt funkció használatához: ");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Alap info lekérdezés: 1");
                System.out.println("Jegybeírás naplóba: 2");
                int input = scanner.nextInt();
                if (input == 1) {
                    SearchInJson.searchStudent();
                    break;
                } else if (input == 2) {
                    System.out.println("Add meg a diák nevét majd a tantárgy nevét");
                    break;
                } else {
                    System.out.println("Rossz szám");
                }
            }
        } catch (Exception e) {
            System.out.println("Csak 1 és 2 közül válszthatsz!!!");
        }
    }

    public static void osztalyMenu() {
        clearConsole();
        try {
            System.out.println("Írj be egy számot a kívánt funkció használatához: ");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Alap info lekérdezés: 1");
                System.out.println("Jegybeírás naplóba: 2");
                int input = scanner.nextInt();
                if (input == 1) {
                    System.out.println("Add meg az osztály számát");
                    break;
                } else if (input == 2) {
                    System.out.println("Add meg az osztály számát majd a tantárgy nevét");
                    break;
                } else {
                    System.out.println("Rossz szám");
                }
            }
        } catch (Exception e) {
            System.out.println("Csak 1 és 2 közül válszthatsz!!!");
        }
    }
}
