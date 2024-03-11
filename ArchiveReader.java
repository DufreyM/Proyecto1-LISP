import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArchiveReader {

    public static void main(String[] args) {
        ArchiveReader interpretador = new ArchiveReader();
        interpretador.interpretador("PRUEBA.txt");
    }

    public void interpretador(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int caracter;
            boolean dentroDefun = false;
            boolean leyendoNombreFuncion = false;
            boolean leyendoParametros = false;
            StringBuilder nombreFuncion = new StringBuilder();
            List<String> parametros = new ArrayList<>();

            while ((caracter = br.read()) != -1) {
                char c = (char) caracter;

                if (c == '(' && !dentroDefun) {
                    dentroDefun = true;
                    leyendoNombreFuncion = true;
                    continue;
                }

                if (c == '(' && dentroDefun) {
                    // Comenzamos a leer los parámetros
                    leyendoNombreFuncion = false;
                    leyendoParametros = true;
                    continue;
                }

                if (c == ')' && dentroDefun && !leyendoParametros) {
                    // Fin de la definición de la función
                    dentroDefun = false;
                    procesarDefun(nombreFuncion.toString(), parametros);
                    nombreFuncion.setLength(0);
                    parametros.clear();
                    continue;
                }

                if (c == ')' && dentroDefun && leyendoParametros) {
                    // Fin de los parámetros
                    leyendoParametros = false;
                    continue;
                }

                if (dentroDefun && leyendoNombreFuncion) {
                    if (c != ' ') {
                        nombreFuncion.append(c);
                    } else {
                        leyendoNombreFuncion = false;
                    }
                    continue;
                }

                if (dentroDefun && leyendoParametros) {
                    if (c != ' ') {
                        if (c != '(' && c != ')') {
                            parametros.add(Character.toString(c));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void procesarDefun(String nombreFuncion, List<String> parametros) {
        System.out.println("Función definida: " + nombreFuncion);
        System.out.println("Parámetros: " + parametros);
    }
}