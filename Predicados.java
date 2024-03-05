import java.util.List;

public class Predicados {
    
//Métodos para determinar si es un átomo o una lista 
/**
 * @param valor : Valor a evaluar para determinar  si es un átomo.
 * @return true si el valor es un átomo, false en caso contrario.
 */
public boolean isAtom(Object valor) {
    //Determino si es una instancia de las diferentes clases de datos en Java
    if (valor instanceof Integer || valor instanceof Float || valor instanceof Double || valor instanceof String) {
        return true;
    } else {
        return false;
    }
}

/**
 * @param valor : Valor a evaluar en el predicado, para determinar si es una lista.
 * @return
 */
public boolean isList(Object valor) {
        return valor instanceof List;
    }
}
