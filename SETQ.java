import java.util.HashMap;
import java.util.Map;

/**
 * Clase que permite al usuario establecer variables y sus valores.
 */

public class SETQ {
    public Map<String, Integer> variables;

    public SETQ() {
        this.variables = new HashMap<>();
    }

    /**
     * Procesa la cadena de entrada para asignar valores a variables
     * @param input La cadena de entrada que contiene la asignación de variable y valor
     * @throws arroja IllegalArgumentException si la entrada se ingresa de manera errónea
     */
    public void processSetq(String input) {
        if (!input.startsWith("(setq ") || !input.endsWith(")")) {
            throw new IllegalArgumentException("Formato incorrecto para SETQ.");
        }
        String[] parts = input.substring(6, input.length() - 1).split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Formato incorrecto para SETQ.");
        }
        String variableName = parts[0];
        int value = Integer.parseInt(parts[1]);
        variables.put(variableName, value);
    }
    /**
     * Obtiene el mapa de variables y sus valores.
     * @return El mapa de variables y sus valores.
     */
    public Map<String, Integer> getVariables() {
        return variables;
    }
}