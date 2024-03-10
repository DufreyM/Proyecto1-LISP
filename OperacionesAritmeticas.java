public class OperacionesAritmeticas {
    private CustomStack<String> stack = new CustomStack<>();

    public int evaluateExpression(String expression) {
        String[] tokens = expression.replace("(", " ( ").replace(")", " ) ").trim().split("\\s+");
        for (int i = tokens.length - 1; i >= 0; i--) {
            stack.push(tokens[i]);
        }
        return evaluateStack();
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
                result = evaluateStack() / evaluateStack();
            }
            stack.pop(); // Remove ")"
            return result;
        } else {
            return Integer.parseInt(token);
        }
    }
}
