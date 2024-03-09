import java.util.Scanner;

public class Controlador {
    private Scanner scanner;
    private SETQ setq;

    public Controlador(Scanner scanner) {
        this.scanner = scanner;
        this.setq = new SETQ();
    }

    public void run() {
        OperacionesAritmeticas operaciones = new OperacionesAritmeticas();
        COND cnd = new COND();
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
                Scanner scn = new Scanner(System.in);
                System.out.println("Ingrese la instrucción SETQ (en formato (setq variable valor)): ");
                String input = scn.nextLine().trim();
                try {
                    setq.processSetq(input);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                    break;
                case 5:
                    System.out.println("Utilizar predicados (ATOM, LIST, EQUAL, <, >)...");
                    break;

                    case 6:
                    scn = new Scanner(System.in);
                    System.out.println("Ingrese las condiciones y expresiones en formato (cond (operador valor1 valor2)): ");
                    String inputc = scn.nextLine().trim();
                    try {
                        if (!inputc.startsWith("(cond (") || !inputc.endsWith("))")) {
                            throw new IllegalArgumentException("Formato incorrecto para condición.");
                        }
                        String innerinputc = inputc.substring(7, inputc.length() - 2).trim();
                        String[] parts = innerinputc.split("\\s+");
                        if (parts.length != 3) {
                            throw new IllegalArgumentException("Formato incorrecto para condición.");
                        }
                        String operator = parts[0];
                        int value1 = Integer.parseInt(parts[1]);
                        int value2 = Integer.parseInt(parts[2]);
                        String result = cnd.evaluateCond(operator, value1, value2);
                        System.out.println("Resultado de COND: " + result);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
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