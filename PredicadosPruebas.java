import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class PredicadosPruebas {

    @Test
    public void testIsAtom() {
        Predicados predicados = new Predicados();
        assertTrue(predicados.isAtom(5)); // Entero
        assertTrue(predicados.isAtom(3.14)); // Doble
        assertTrue(predicados.isAtom("hello")); // String
        assertFalse(predicados.isAtom(new ArrayList<>())); // Lista
    }

    @Test
    public void testIsList() {
        Predicados predicados = new Predicados();
        assertTrue(predicados.isList(new ArrayList<>())); // Lista vacía
        assertTrue(predicados.isList(List.of(1, 2, 3))); // Lista de enteros
        assertFalse(predicados.isList(5)); // Entero
        assertFalse(predicados.isList("hello")); // String
    }

    @Test
    public void testComparar() {
        Comparator<Integer> comparador = Comparator.naturalOrder();
        assertTrue(Predicados.comparar(5, 5, comparador)); // Igualdad
        assertFalse(Predicados.comparar(5, 10, comparador)); // No igual
    }

    @Test
    public void testEsMayorQue() {
        Comparator<Integer> comparador = Comparator.naturalOrder();
        assertTrue(Predicados.esMayorQue(10, 5, comparador)); // Mayor que
        assertFalse(Predicados.esMayorQue(5, 10, comparador)); // No mayor que
    }

    @Test
    public void testEsMenorQue() {
        Comparator<Integer> comparador = Comparator.naturalOrder();
        assertTrue(Predicados.esMenorQue(5, 10, comparador)); // Menor que
        assertFalse(Predicados.esMenorQue(10, 5, comparador)); // No menor que
    }
}