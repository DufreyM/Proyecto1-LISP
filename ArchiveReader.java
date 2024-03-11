import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArchiveReader {

    private Map<String, String> funciones = new HashMap<>();

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
        boolean dentroSetQ = false;
        StringBuilder nombreFuncion = new StringBuilder();
        StringBuilder nombresetQ = new StringBuilder();
        List<String> parametros = new ArrayList<>();
        List<String> infoSetQ = new ArrayList<>();
        StringBuilder cuerpoFuncion = new StringBuilder();
        
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("(defun")) {
                dentroDefun = true;
                nombreFuncion.setLength(0);
                parametros.clear();
                cuerpoFuncion.setLength(0);

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
            } else if (dentroDefun) {
                // Construir el cuerpo de la función
                if (!line.isEmpty()) {
                    cuerpoFuncion.append(line).append("\n");
                }
                
                // Verificar si es el final de la definición de función
                if (line.endsWith(")")) {
                    dentroDefun = false;
                    procesarDefun(nombreFuncion.toString(), parametros, cuerpoFuncion.toString());
                }
            } else if(line.startsWith("(setQ")){
                int inicioNombre = line.indexOf("(setQ") + 6; // Longitud de "(setQ "
                int finNombre = line.indexOf(' ', inicioNombre);
                nombresetQ.append(line.substring(inicioNombre, finNombre));

                int inicioParametros = line.indexOf('(', finNombre);
                int finParametros = line.indexOf(')', inicioParametros);
                String[] params = line.substring(inicioParametros + 1, finParametros).split(" ");
                for (String param : params) {
                    if (!param.isEmpty()) {
                        infoSetQ.add(param);
                    }
                }
                procesarsetQ(nombresetQ.toString(), infoSetQ);

            } else if(!line.startsWith("(defun")){
                for (String nombre : funciones.keySet()) {
                    if (line.contains("(" + nombre)) {
                        llamadaFun(nombre, line);
                        break; 
                    }
                }
            }
        }
    }

    private void procesarDefun(String nombreFuncion, List<String> parametros, String cuerpoFuncion) {
        System.out.println("Función definida: " + nombreFuncion);
        System.out.println("Parámetros: " + parametros);
        System.out.println("Cuerpo de la función:\n" + cuerpoFuncion);
        funciones.put(nombreFuncion, cuerpoFuncion);
    }

    private void procesarsetQ(String nombreVariable, List<String> Valores){
        System.out.println("SetQ definido: " + nombreVariable);
        System.out.println("Parámetros:" + Valores);
    }

    private void llamadaFun(String nombreFun, String llamada){
        System.out.println("Llamada a la función: " + nombreFun);
        System.out.println("Con instrucciones:" + funciones.get(nombreFun));
    }
    // private void llamadaFun(String nombreFun, String llamada){
    //     System.out.println("Llamada a la función: " + nombreFun);
    //     System.out.println("Con instrucciones " + llamada);

    //     String[] partes = llamada.split("\\s+");
    //     String nombreFuncion = partes[0].substring(1); // Eliminar el paréntesis "("
    //     String argumentos = llamada.substring(llamada.indexOf("(") + 1, llamada.indexOf(")"));

    //     // Buscar la definición de la función correspondiente
    //     String cuerpoFuncion = funciones.get(nombreFuncion);
        
    //     if (cuerpoFuncion != null) {
    //         // Reemplazar los parámetros con los argumentos
    //         for (int i = 0; i < parametros.size(); i++) {
    //             cuerpoFuncion = cuerpoFuncion.replaceAll(parametros.get(i), partes[i + 1]);
    //         }

    //         // Ejecutar el cuerpo de la función (aquí puedes implementar la lógica para ejecutar el cuerpo de la función)
    //         System.out.println("Llamada a la función: " + nombreFuncion);
    //         System.out.println("Con argumentos: " + argumentos);
    //         System.out.println("Cuerpo de la función ejecutado con argumentos:");
    //         System.out.println(cuerpoFuncion);
    //     } else {
    //         System.out.println("La función '" + nombreFuncion + "' no está definida.");
    //     }
    // }


}