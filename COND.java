import java.util.Map;

/**
 * Clase que contiene métodos para evaluar condiciones.
 */

public class COND {
    /**
     * Evalúa una condición simple.
     * @param operator Describe el operador de comparación (> = <)
     * @param value1 El primer valor que el usuario quiera comparar
     * @param value2 El segundo valor que el usuario quiera comparar
     * @return "válido" si la condición se cumple, "no válido" de lo contrario
     */
    public String evaluateCond(String operator, int value1, int value2) {
        switch (operator) {
            case ">":
                if (value1 > value2) {
                    return "válido";
                }
                break;
            case "=":
                if (value1 == value2) {
                    return "válido";
                }
                break;
            case "<":
                if (value1 < value2) {
                    return "válido";
                }
                break;
            default:
                throw new IllegalArgumentException("Operador no válido: " + operator);
        }
        return "no válido";
    }

    /**
     * Evalúa una condición compuesta.
     * @param conditions Un array de condiciones en formato de cadena.
     * @param variables Un mapa que contiene las variables y sus valores.
     * @return "válido" si la condición se cumple, "no válido" de lo contrario.
     */
    public String evaluateCond(String[] conditions, Map<String, Integer> variables) {
        if (conditions.length != 1) {
            throw new IllegalArgumentException("Formato incorrecto para condición.");
        }
        String condition = conditions[0].trim();
        if (!condition.startsWith("(") || !condition.endsWith(")")) {
            throw new IllegalArgumentException("Formato incorrecto para condición.");
        }
        String innerCondition = condition.substring(1, condition.length() - 1).trim();
        String[] parts = innerCondition.split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Formato incorrecto para condición.");
        }
        String operator = parts[0];
        int value1 = getValue(parts[1], variables);
        int value2 = getValue(parts[2], variables);
        return evaluateCond(operator, value1, value2);
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
}
