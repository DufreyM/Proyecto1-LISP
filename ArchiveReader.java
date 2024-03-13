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

    private void procesarDefun(String nombreFuncion, List<String> parametros, String cuerpoFuncion) {
        deFun nuevaFuncion = new deFun(nombreFuncion, parametros, cuerpoFuncion);
        listaDeFuns.add(nuevaFuncion);
    }

    private boolean isFunctionCall(String line) {
        for (deFun fun : listaDeFuns) {
            if (line.contains(fun.getDeFunname())) {
                return true; // La línea contiene el nombre de una función definida.
            }
        }
        return false; // No se encontró ninguna función definida en la línea.
    }

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

    private Object evaluarExpresion(String expresion) {
        return expresion;
    }
}
