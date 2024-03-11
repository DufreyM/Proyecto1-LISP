public class Quote {
    public static void eval(String expression) {
        String trimmedExpression = expression.trim();

        if (trimmedExpression.toLowerCase().startsWith("quote ")) {
            // Imprimir la parte de la cadena después de la palabra "quote"
            String result = trimmedExpression.substring(6).trim(); // 6 es la longitud de "quote "
            System.out.println("Resultado: " + result);
        } else if (trimmedExpression.equals("'")) {
            System.out.println("Expresión no válida: debe haber un valor después de la comilla simple.");
        } else if (trimmedExpression.startsWith("'")) {
            // Imprimir la parte de la cadena después de la comilla simple
            String result = trimmedExpression.substring(1).trim();
            System.out.println("Resultado: " + result);
        } else {
            System.out.println("Expresión no válida: debe comenzar con 'quote' o estar entre comillas simples.");
        }
    }
}