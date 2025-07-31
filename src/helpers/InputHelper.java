package helpers;
import java.util.Scanner;

public class InputHelper {
    public static String textInput(String label){
        Scanner scanner = scanner();
        System.out.print(label + ": ");
        return scanner.nextLine();
    }

    public static int intInput(String label) {
        Scanner scanner = scanner();
        System.out.print(label + ": ");
        return scanner.nextInt();
    }

    public static float floatInput(String label) {
        Scanner scanner = scanner();
        System.out.print(label + ": ");
        return scanner.nextFloat();
    }

    private static Scanner scanner(){
        return new Scanner(System.in);
    }
}
