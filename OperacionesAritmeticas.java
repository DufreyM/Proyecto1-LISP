public class OperacionesAritmeticas {
    private CustomStack<String> stack = new CustomStack<>();

    /**
     * Evalúa una expresión aritmética dada como una cadena de texto.
     * 
     * @param expression La expresión aritmética a evaluar, como una cadena de texto. La expresión debe
     *                   estar correctamente formateada y puede incluir los operadores +, -, *, / y 
     *                   paréntesis para denotar precedencia.
     * @return El resultado de la evaluación de la expresión aritmética como un entero.
     * @throws ArithmeticException Si la expresión incluye una división por cero.
     * @throws IllegalArgumentException Si se encuentra un operador inválido o un token no válido.
     */
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

    /**
     * Método privado para evaluar la pila de tokens y calcular el resultado de la expresión.
     * 
     * Este método es recursivo y se llama a sí mismo para evaluar subexpresiones basadas en la
     * precedencia de operadores definida por paréntesis.
     * 
     * @return El resultado de la evaluación de la expresión aritmética actual como un entero.
     * @throws ArithmeticException Si la expresión incluye una división por cero.
     * @throws IllegalArgumentException Si se encuentra un operador inválido o un token no válido.
     */
    private int evaluateStack() {
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
