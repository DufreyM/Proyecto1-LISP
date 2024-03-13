public class Quote {
    public static String eval(String expression) {
        String trimmedExpression = expression.trim();

        if (trimmedExpression.toLowerCase().startsWith("quote ")) {
            // Retorna lo que le sigue a quote
            return trimmedExpression.substring(6).trim(); // quote tiene 6
        } else if (trimmedExpression.equals("'")) {
            return "Expresión no válida: debe haber un valor después de la comilla simple.";
        } else if (trimmedExpression.startsWith("'")) {
            // Retorna lo que le sigue a la '
            return trimmedExpression.substring(1).trim();
        } else if (trimmedExpression.toLowerCase().startsWith("(quote ")) {
            // Retorna lo que le sigue a quote
            return trimmedExpression.substring(7).trim();
        }
         else {
            return "Expresión no válida: debe comenzar con 'quote' o estar entre comillas simples.";
        }
    }
}