import java.util.HashMap;

public class SETQ {
    private HashMap<String, Integer> variables;

    public SETQ() {
        this.variables = new HashMap<>();
    }

    public void setq(String variableName, int value) {
        variables.put(variableName, value);
    }

    public int getValue(String variableName) {
        if (variables.containsKey(variableName)) {
            return variables.get(variableName);
        } else {
            throw new IllegalArgumentException("Variable " + variableName + " no definida.");
        }
    }
}
