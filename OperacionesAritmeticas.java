public class OperacionesAritmeticas {
    private CustomStack<Integer> stack;

    public OperacionesAritmeticas() {
        this.stack = new CustomStack<>();
    }

    public int evaluatePostfix(String expression) throws IllegalArgumentException {
        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(Integer.valueOf(token));
            } else {
                performOperation(token);
            }
        }
        //Verifica si el stack esta vacio. 
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression: insufficient operands or too many operators");
        }

        return stack.pop();
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void performOperation(String operator) {
        //Verifica si el archivo tipo .txt se quedo sin datos. 
        if (stack.isEmpty()) {
            throw new IllegalArgumentException("Without further information, please add more operands");
        }
    
        int oper1 = stack.pop();

        //Verifica si el archivo tipo .txt se quedo sin datos. 
        if (stack.isEmpty()) {
            throw new IllegalArgumentException("Without further information, please add more operands");
        }
    
        int oper2 = stack.pop();

        //Switch para la calculadora
        switch (operator) {
            case "+":
                stack.push(oper2 + oper1);
                break;
            case "-":
                stack.push(oper2 - oper1);
                break;
            case "*":
                stack.push(oper2 * oper1);
                break;
            case "/":
                if (oper1 == 0) {
                    //Excepción por si se quiere dividir dentro de 0. 
                    throw new IllegalArgumentException("Division by zero is not allowed");
                }
                stack.push(oper2 / oper1);
                break;
            default:
            //Manejo de errores
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}