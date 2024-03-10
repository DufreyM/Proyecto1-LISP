import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArchiveReader {

    public static void main(String[] args) {
        ArchiveReader reader = new ArchiveReader();
        reader.interpretador("PRUEBA.txt");
    }

    public void interpretador(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            boolean dentroDefun = false;
            String nombreFuncion = null;
            List<String> parametros = new ArrayList<>();

            while ((linea = br.readLine()) != null) {
                String[] tokens = linea.trim().split("\\s+");

                for (int i = 0; i < tokens.length; i++) {
                    String token = tokens[i];

                    if (token.equals("(defun")) {
                        dentroDefun = true;
                        nombreFuncion = tokens[i + 1];
                        continue;
                    }

                    if (dentroDefun && token.equals("(")) {
                        // Comenzamos a leer los parámetros
                        parametros.clear(); // Limpiamos la lista de parámetros
                        for (int j = i + 1; j < tokens.length; j++) {
                            if (tokens[j].equals(")")) {
                                // Fin de los parámetros
                                break;
                            }
                            parametros.add(tokens[j]);
                        }
                        continue;
                    }

                    if (dentroDefun && token.equals(")")) {
                        dentroDefun = false;
                        procesarDefun(nombreFuncion, parametros);
                        nombreFuncion = null; // Limpiamos el nombre de la función
                        parametros.clear(); // Limpiamos la lista de parámetros
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
