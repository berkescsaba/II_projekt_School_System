import java.util.Scanner;

public class MenuSelect {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void mainMenu() {
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
                    tanarMenu();
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
        try {
            System.out.println("Írj be egy számot a kívánt funkció használatához: ");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Alap info lekérdezés: 1");
                System.out.println("Jegybeírás naplóba: 2");
                int input = scanner.nextInt();
                if (input == 1) {
                    System.out.println("Add meg a diák nevét");
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
    public static void tanarMenu() {
        try {
            System.out.println("Írd be a tanár kereszt vagy vezetéknevét:");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.nextLine();
                SearchInJson.searchTeacher(input);
//                if (input == 1) {
//                    System.out.println("Add meg a diák nevét");
//                    break;
//                } else if (input == 2) {
//                    System.out.println("Add meg a diák nevét majd a tantárgy nevét");
//                    break;
//                } else {
//                    System.out.println("Rossz szám");
//                }

            }
        } catch (Exception e) {
            System.out.println("Csak 1 és 2 közül válszthatsz!!!");
        }
    }
}
