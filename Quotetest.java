import org.junit.Test;

public class Quotetest {

    @Test
    public void testEval_QuoteExpression() {
        String expression = "quote This is a test";
        Quote.eval(expression);
        //"Resultado: This is a test"
    }

    @Test
    public void testEval_SingleQuoteExpression() {
        String expression = "'This is a test";
        Quote.eval(expression);
        //"Resultado: This is a test"
    }

    @Test
    public void testEval_InvalidExpression() {
        String expression = "Invalid expression";
        Quote.eval(expression);
        //"Expresión no válida: debe comenzar con 'quote' o estar entre comillas simples."
    }

    @Test
    public void testEval_EmptyString() {
        String expression = "";
        Quote.eval(expression);
        //"Expresión no válida: debe comenzar con 'quote' o estar entre comillas simples."
    }

    @Test
    public void testEval_EmptyQuote() {
        String expression = "'";
        Quote.eval(expression);
        //"Expresión no válida: debe haber un valor después de la comilla simple."
    }

    @Test
    public void testEval_WhitespaceQuote() {
        String expression = "'    ";
        Quote.eval(expression);
        //"Expresión no válida: debe haber un valor después de la comilla simple."
    }
}
