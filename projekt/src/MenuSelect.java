import java.util.Scanner;

public class MenuSelect {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void mainMenu() {
        boolean validInput = false;
        do {
            try {

                Scanner scanner = new Scanner(System.in);
                System.out.println("Üdvözöllek!");
                System.out.println("Írj be egy számot a kívánt funkció használatához: ");
                System.out.println("Diák művelet: 1");
                System.out.println("Osztály művelet: 2");
                int input = scanner.nextInt();
                if (input == 1) {
                    System.out.println("Diák műveletek");
                } else if (input == 2) {
                    System.out.println("Osztály művelet");
                } else {
                    System.out.println("Rossz szám");
                }
            } catch (Exception e) {
                System.out.println("Csak 1 és 2 közül válszthatsz!!!");
            }

        } while (!validInput);


    }
}
