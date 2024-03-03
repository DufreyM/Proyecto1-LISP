
import java.util.Scanner;
public class Controlador {
    private Scanner scanner;

    public Controlador(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        OperacionesAritmeticas operaciones = new OperacionesAritmeticas();
        boolean exit = false;
        while (!exit) {
            displayMenu();
            int option = getUserInput();
            switch (option) {
                case 1:
                System.out.println("Operaciones Aritmeticas");
                System.out.println("Escribe tu expresión Lisp o escribe 'exit' para salir.");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                try {
                    int result = operaciones.evaluatePostfix(line);
                    System.out.println("Resultado: " + result);

                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            
                    break;
                    
                case 2:
                    System.out.println("Usar instrucción QUOTE...");
                    break;
                case 3:
                    System.out.println("Definir funciones (DEFUN)...");
                    break;
                case 4:
                    System.out.println("Usar SETQ...");
                    break;
                case 5:
                    System.out.println("Utilizar predicados (ATOM, LIST, EQUAL, <, >)...");
                    break;
                case 6:
                    System.out.println("Usar condicionales (COND)...");
                    break;
                case 7:
                    System.out.println("Pasar parámetros...");
                    break;
                case 8:
                    exit = true;
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
            }
        }
    
}


    private void displayMenu() {
        System.out.println("Menú:");
        System.out.println("1. Operaciones aritméticas");
        System.out.println("2. Instrucción QUOTE");
        System.out.println("3. Definición de funciones (DEFUN)");
        System.out.println("4. SETQ");
        System.out.println("5. Predicados (ATOM, LIST, EQUAL, <, >)");
        System.out.println("6. Condicionales (COND)");
        System.out.println("7. Paso de parámetros");
        System.out.println("8. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private int getUserInput() {
        return scanner.nextInt();
    }
}