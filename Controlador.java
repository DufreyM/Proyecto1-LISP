import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Controlador {
    private Scanner scanner;
    private Map<String, Integer> variables;

    public Controlador(Scanner scanner) {
        this.scanner = scanner;
        this.variables = new HashMap<>();
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
                SETQ sq = new SETQ();
                Scanner scn = new Scanner(System.in);
                System.out.println("Ingrese la instrucción SETQ (en formato (setq variable valor)): ");
                String setqInput = scn.nextLine().trim();
                try {
                    if (!setqInput.startsWith("(setq ") || !setqInput.endsWith(")")) {
                        throw new IllegalArgumentException("Formato incorrecto para SETQ.");
                    }
                    String[] parts = setqInput.substring(6, setqInput.length() - 1).split("\\s+");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Formato incorrecto para SETQ.");
                    }
                    String variableName = parts[0];
                    int value = Integer.parseInt(parts[1]);
                    sq.setq(variableName, value);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                    break;
                case 5:
                    System.out.println("Utilizar predicados (ATOM, LIST, EQUAL, <, >)...");
                    break;
                case 6:
                    System.out.println("Aquí va COND...");
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