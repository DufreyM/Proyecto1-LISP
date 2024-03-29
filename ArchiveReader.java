import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que implementa un interpretador básico para procesar y ejecutar comandos desde un archivo.
 * Capaz de manejar definiciones de funciones, asignaciones de variables, y evaluaciones de expresiones.
 */
public class ArchiveReader {

    private COND cond = new COND();
    private List<deFun> listaDeFuns = new ArrayList<>();
    private SETQ setq = new SETQ();
    private Predicados predicados = new Predicados();
    private OperacionesAritmeticas opp = new OperacionesAritmeticas();

    /**
     * Lee y procesa el contenido de un archivo de texto para ejecutar las instrucciones definidas.
     * @param filePath Ruta del archivo a interpretar.
     */
    public void interpretador(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            String line;

            // Leer el contenido completo del archivo
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }

            // Procesar el contenido en busca de definiciones de función
            processContent(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Procesa el contenido del archivo, identificando y ejecutando definiciones de función, asignaciones y otras expresiones.
     * @param content Contenido completo del archivo a procesar.
     */
    private void processContent(String content) {
        String[] lines = content.split("\n");
        boolean dentroDefun = false;
        StringBuilder nombreFuncion = new StringBuilder();
        List<String> parametros = new ArrayList<>();
        StringBuilder cuerpoFuncion = new StringBuilder();

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("(defun")) {
                dentroDefun = true;
                nombreFuncion.setLength(0);
                parametros.clear();
                cuerpoFuncion.setLength(0);

                // Extraer el nombre de la función
                int inicioNombre = line.indexOf("(defun") + 7; // Longitud de "(defun "
                int finNombre = line.indexOf(' ', inicioNombre);
                nombreFuncion.append(line.substring(inicioNombre, finNombre));

                // Extraer los parámetros
                int inicioParametros = line.indexOf('(', finNombre);
                int finParametros = line.lastIndexOf(')');
                String paramsLine = line.substring(inicioParametros + 1, finParametros);
                String[] params = paramsLine.split("\\s");
                for (String param : params) {
                    if (!param.isEmpty()) {
                        parametros.add(param);
                    }
                }
            } else if (line.startsWith("(cond")){
                String[] parts = line.substring(6, line.length() - 1).split("\\s+");
                System.out.println(cond.evaluateCond(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));

            } else if (dentroDefun) {
                // Construir el cuerpo de la función
                if (!line.isEmpty()) {
                    // Agregar la línea al cuerpo de la función
                    cuerpoFuncion.append(line).append("\n");

                    // Verificar si es el final de la definición de función
                    int indexOfClosingParenthesis = line.indexOf("))");
                    if (indexOfClosingParenthesis != -1) {
                        dentroDefun = false;
                        // Tomar solo el contenido hasta el primer paréntesis cerrado
                        cuerpoFuncion.setLength(cuerpoFuncion.length() - (line.length() - indexOfClosingParenthesis));
                        procesarDefun(nombreFuncion.toString(), parametros, cuerpoFuncion.toString());
                    }
                }
            } else if (line.startsWith("(factorial")){
                evaluarFactorial(line);
            } else if (line.startsWith("(setq")) {
                setq.processSetq(line);
            } else if (line.startsWith("(atom")) {
                evaluarAtom(line);
            } else if (!line.startsWith("(defun") && !line.startsWith("(setq")) {
                // Verificar si la línea contiene una llamada a función
                if (isFunctionCall(line)) {
                    String result = processFunction(line);
                    System.out.println("Resultado de la función: " + result + "\n");
                } else {
                    // Si no es una llamada a función, continuar con la lógica actual
                    boolean foundFunction = false;
                    for (deFun fun : listaDeFuns) {
                        if (line.contains(fun.getDeFunname())) {
                            System.out.println("Encontre la funcion" + fun.getDeFunname());
                            System.out.println(fun.getInstrucciones());
                            foundFunction = true;
                        }
                    }

                    if (!foundFunction) {
                        // Buscar quotes
                        if (line.toLowerCase().startsWith("quote ")) {
                            String result = Quote.eval(line);
                            System.out.println("Resultado del quote: " + result + "\n");
                        } else if (isArithmeticOperation(line)) {
                            int result = operateArithmeticExpression(line);
                            System.out.println("Resultado de la operación aritmética: " + result);
                        } else if (line.equals("'")) {
                            System.out.println("Expresión no válida: debe haber un valor después de la comilla simple.");
                        } else if (line.startsWith("'")) {
                            String result = Quote.eval(line);
                            System.out.println("Resultado del quote: " + result + "\n");
                        } else if (line.startsWith("(quote ")) {
                            String result = Quote.eval(line.substring(0, line.length() - 1));
                            System.out.println("Resultado del quote: " + result + "\n");
                        } 
                    }
                }
            } 
        }

        // Imprime las variables que tiene el programa
        System.out.println("\n Estado final de las variables:\n");
        for (Map.Entry<String, Integer> entry : setq.getVariables().entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue() + "\n");
        }
    }

    /**
     * Procesa y almacena la definición de una nueva función.
     * @param nombreFuncion Nombre de la función definida.
     * @param parametros Lista de parámetros de la función.
     * @param cuerpoFuncion Cuerpo de la función.
     */
    private void procesarDefun(String nombreFuncion, List<String> parametros, String cuerpoFuncion) {
        deFun nuevaFuncion = new deFun(nombreFuncion, parametros, cuerpoFuncion);
        listaDeFuns.add(nuevaFuncion);
    }

    /**
     * Verifica si una línea de texto contiene la llamada a una función definida por el usuario.
     * @param line Línea de texto a verificar.
     * @return true si la línea contiene una llamada a función, false en caso contrario.
     */
    private boolean isFunctionCall(String line) {
        for (deFun fun : listaDeFuns) {
            if (line.contains(fun.getDeFunname())) {
                return true; // La línea contiene el nombre de una función definida.
            }
        }
        return false; // No se encontró ninguna función definida en la línea.
    }

    /**
     * Procesa una llamada a función, ejecutando la función y retornando el resultado.
     * @param line Línea de texto que contiene la llamada a la función.
     * @return El resultado de la ejecución de la función.
     */
    private String processFunction(String line) {
        for (deFun fun : listaDeFuns) {
            String funcionLlamada = fun.getDeFunname();
            if (line.contains(funcionLlamada)) {
                // Extraer los argumentos de la llamada a función
                int inicioArgs = line.indexOf(funcionLlamada) + funcionLlamada.length() + 2;
                int finArgs = line.indexOf(')', inicioArgs);
                String argsLine = line.substring(inicioArgs, finArgs);
                String[] argumentos = argsLine.split("\\s+");

                // Procesar argumentos por si contienen llamadas a funciones
                for (int i = 0; i < argumentos.length; i++) {
                    if (isFunctionCall(argumentos[i])) {
                        argumentos[i] = processFunction(argumentos[i]);
                    } else if (setq.getVariables().containsKey(argumentos[i])) {
                        argumentos[i] = setq.getVariables().get(argumentos[i]).toString();
                    }
                }

                // Reconstruir la línea con los argumentos ya procesados
                String instruccionesDepuradas = fun.getInstrucciones();
                for (int i = 0; i < argumentos.length; i++) {
                    instruccionesDepuradas = instruccionesDepuradas.replace(Character.toString((char) ('a' + i)),
                            argumentos[i]);
                }

                // Este método debería implementarse para evaluar la función
                return "La respuesta de la operacion " + fun.getDeFunname() + " es "
                        + opp.evaluateExpression(instruccionesDepuradas);
            }
        }
        // Si la línea no corresponde a una llamada de función, retornar la línea tal cual
        return line;
    }

    /**
     * Evalúa si una expresión dada es un átomo.
     * @param expresion La expresión a evaluar.
     */
    public void evaluarAtom(String expresion) {
        String contenidoExpresion = expresion.substring("(atom ".length(), expresion.length() - 1).trim();

        // Evaluar la expresión y luego determinar si es un átomo
        Object resultado = evaluarExpresion(contenidoExpresion);

        if (predicados.isAtom(resultado)) {
            System.out.println("\nResultado del Atom: true\n");
        } else {
            System.out.println("\nResultado del Atom: false\n");
        }
    }

    /**
     * Opera una expresión aritmética y devuelve el resultado.
     * @param expression La expresión aritmética a operar.
     * @return El resultado de la operación aritmética.
     */
    private int operateArithmeticExpression(String expression) {
        try {
            return opp.evaluateExpression(expression);
        } catch (ArithmeticException e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // Otra acción apropiada en caso de excepción
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // Otra acción apropiada en caso de excepción
        }
    }

    /**
     * Evalúa una expresión. Este es un método stub en el código proporcionado.
     * @param expresion La expresión a evaluar.
     * @return El resultado de la evaluación.
     */
    private Object evaluarExpresion(String expresion) {
        return expresion;
    }
    
    private int getValue(String input, Map<String, Integer> variables) {
        if (variables.containsKey(input)) {
            return variables.get(input);
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El valor '" + input + "' no es un número o variable válida.");
        }
    }

    private void evaluarFactorial(String line) {
        // Extraer argumento de la llamada a la función factorial
        int inicioArgs = line.indexOf("(factorial") + "(factorial".length() + 1;
        int finArgs = line.indexOf(')', inicioArgs);
        String argLine = line.substring(inicioArgs, finArgs);

        // Evaluar el argumento 
        int argumento = getValue(argLine, setq.getVariables());

        // Calcular el factorial y mostrar el resultado
        int resultado = calcularFactorial(argumento);
        System.out.println("El factorial de " + argumento + " es: " + resultado + "\n");
    }

    /**
     * Verifica si una línea es una operación aritmética.
     * @param line La línea a verificar.
     * @return true si la línea es una operación aritmética, false en caso contrario.
     */
    private boolean isArithmeticOperation(String line) {
        String arithmeticPattern = ".[0-9]+[\\+\\-\\/\\(\\)].*";
        // Si la línea coincide con el patrón, se considera una operación aritmética
        return line.matches(arithmeticPattern);
    }

    private int calcularFactorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * calcularFactorial(n - 1);
    }    
}
