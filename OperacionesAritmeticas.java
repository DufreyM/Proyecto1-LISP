/*
 * Universidad del Valle de Guatemala
 * Algoritmos y Estructura de Datos – sección 40
 * Roberto Barreda – 23354 | Mia Fuentes - 23775
 */

 import java.util.EmptyStackException;
 import java.util.Stack;

public class OperacionesAritmeticas {
    private Stack<Integer> stack;

    public OperacionesAritmeticas() {
        stack = new Stack<>();
    }

    public int evaluatePostfix(String expression) throws IllegalArgumentException {
        // Dividir la expresión en tokens separados por espacios.
        String[] tokens = expression.split(" ");

        for (String token : tokens) {
            if (isOperand(token)) {
                stack.push(Integer.parseInt(token));
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Insufficient operands for operation: " + token);
                }

                int operand2 = stack.pop();
                int operand1 = stack.pop();

                int result = performOperation(token.charAt(0), operand1, operand2);
                stack.push(result);
            } else {
                throw new IllegalArgumentException("Invalid token in expression: " + token);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression format");
        }

        return stack.pop();
    }
    
    //Verifica si un token es un operando
    private boolean isOperand(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Realiza la operación especificada entre dos operandos.
     *
     * @param operator El operador a aplicar.
     * @param operand1 El primer operando.
     * @param operand2 El segundo operando.
     * @return El resultado de la operación.
     * @throws IllegalArgumentException Si se intenta dividir por cero.
     */
    
    private int performOperation(char operador, int operand1, int operand2) {
        switch (operador) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operador);
        }
    }


    private boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

}