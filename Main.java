import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Controlador interpreterMenu = new Controlador(scanner);
        interpreterMenu.run();
    }
}