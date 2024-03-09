import java.util.HashMap;
import java.util.Map;

public class SETQ {
    private Map<String, Integer> variables;

    public SETQ() {
        this.variables = new HashMap<>();
    }

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

    public Map<String, Integer> getVariables() {
        return variables;
    }
}