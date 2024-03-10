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
                    System.out.println("Operaciones Aritméticas (Lisp)");
                    solicitarExpresionLisp(operaciones);
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
    
    private void solicitarExpresionLisp(OperacionesAritmeticas operaciones) {
        System.out.println("Escribe tu expresión Lisp o escribe 'exit' para salir:");
        String expression = scanner.nextLine().trim(); 
        while (!expression.equalsIgnoreCase("exit")) {
            try {
                int result = operaciones.evaluateExpression(expression);
                System.out.println("Resultado: " + result);
            } catch (Exception e) {
                System.out.println("Error en la expresión. Intenta nuevamente.");
            }
            System.out.println("Escribe tu expresión Lisp o escribe 'exit' para salir:");
            expression = scanner.nextLine().trim();
        }
    }

    private void displayMenu() {
        System.out.println("\nMenú:");
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
        int input = 0;
        boolean valid = false;
        while (!valid) {
            try {
                input = Integer.parseInt(scanner.nextLine().trim()); // Lee la línea completa y convierte a entero
                valid = true; // Salir del bucle si la conversión es exitosa
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
            }
        }
        return input;
    }
}
