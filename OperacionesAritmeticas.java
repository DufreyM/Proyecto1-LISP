public class OperacionesAritmeticas {
    private CustomStack<String> stack = new CustomStack<>();

    public int evaluateExpression(String expression) {
        String[] tokens = expression.replace("(", " ( ").replace(")", " ) ").trim().split("\\s+");
        for (int i = tokens.length - 1; i >= 0; i--) {
            stack.push(tokens[i]);
        }
        try {
            return evaluateStack();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }

    int evaluateStack() {
        String token = stack.pop();
        if (token.equals("(")) {
            int result = 0;
            String operator = stack.pop();
            if (operator.equals("+")) {
                result = evaluateStack() + evaluateStack();
            } else if (operator.equals("-")) {
                result = evaluateStack() - evaluateStack();
            } else if (operator.equals("*")) {
                result = evaluateStack() * evaluateStack();
            } else if (operator.equals("/")) {
                int divisor = evaluateStack();
                int dividend = evaluateStack();
                if (divisor == 0) {
                    throw new ArithmeticException("División por cero");
                }
                result = dividend / divisor;
            } else {
                throw new IllegalArgumentException("Operador inválido: " + operator);
            }
            stack.pop(); // Remove ")"
            return result;
        } else {
            try {
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Token no válido: " + token);
            }
        }
    }
}
