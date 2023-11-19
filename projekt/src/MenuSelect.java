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
                int input = scanner.nextInt();
                if (input == 1) {
                    System.out.println("Diák műveletek");
                    diakMenu();
                    break;
                } else if (input == 2) {
                    System.out.println("Osztály művelet");
                    osztalyMenu();
                    break;
                } else {
                    System.out.println("Rossz szám");
                }
            }
        } catch (Exception e) {
            System.out.println("Csak 1 és 2 közül válszthatsz!!!");
        }
    }

    public static void diakMenu() {
        System.out.println("Írj be egy számot a kívánt funkció használatához: ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Alap info lekérdezés: 1");
            System.out.println("Jegybeírás naplóba: 2");
            int input = scanner.nextInt();
            if (input == 1){
                System.out.println("Add meg a diák nevét");
                break;
            } else if (input == 2) {
                System.out.println("Add meg a diák nevét majd a tantárgy nevét");
                break;
            }

        }
    }

    public static void osztalyMenu() {
        System.out.println("Írj be egy számot a kívánt funkció használatához: ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Alap info lekérdezés: 1");
            System.out.println("Jegybeírás naplóba: 2");
            int input = scanner.nextInt();
            if (input == 1){
                System.out.println("Add meg az osztály számát");
                break;
            } else if (input == 2) {
                System.out.println("Add meg az osztály számát majd a tantárgy nevét");
                break;
            }

        }
    }
}
