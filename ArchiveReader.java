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
            StringBuilder content = new StringBuilder();
            String line;

            // Leer el contenido completo del archivo
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }

            // Procesar el contenido en busca de definiciones de función
            processContent(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processContent(String content) {
        String[] lines = content.split("\n");
        boolean dentroDefun = false;
        StringBuilder nombreFuncion = new StringBuilder();
        List<String> parametros = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("(defun")) {
                dentroDefun = true;
                nombreFuncion.setLength(0);
                parametros.clear();

                // Extraer el nombre de la función
                int inicioNombre = line.indexOf("(defun") + 7; // Longitud de "(defun "
                int finNombre = line.indexOf(' ', inicioNombre);
                nombreFuncion.append(line.substring(inicioNombre, finNombre));

                // Extraer los parámetros
                int inicioParametros = line.indexOf('(', finNombre);
                int finParametros = line.indexOf(')', inicioParametros);
                String[] params = line.substring(inicioParametros + 1, finParametros).split(" ");
                for (String param : params) {
                    if (!param.isEmpty()) {
                        parametros.add(param);
                    }
                }
                procesarDefun(nombreFuncion.toString(), parametros);
            } else if (dentroDefun) {
                // Aquí podrías manejar el cuerpo de la función si fuera necesario
                // Por simplicidad, solo ignoramos cualquier línea dentro de una definición de función
            }
        }
    }

    private void procesarDefun(String nombreFuncion, List<String> parametros) {
        System.out.println("Función definida: " + nombreFuncion);
        System.out.println("Parámetros: " + parametros);
    }
}
