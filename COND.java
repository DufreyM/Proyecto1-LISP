import java.util.Map;

public class COND {
    public COND() {
    }

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
