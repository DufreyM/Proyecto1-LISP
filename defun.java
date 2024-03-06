import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class defun {
    private String defunname;
    private String Parametros;
    private String[] Instrucciones;
    private CustomStack<String> stack;
    private HashMap<String, Integer> Variables = new HashMap<>();
    private ArrayList<String> Lineas = new ArrayList<>();

    public defun(String defunname, String Parametros, String Instrucciones){
        this.defunname = defunname;
    }
    
    public ArrayList<String> LeerArchivosdefun(String Camino){
        try {
            File archivo = new File(Camino);
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                Lineas.add(linea); 
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        }
        return this.Lineas;
    }

    public String Leerdefun(String Instrucciones){
        defun NuevaFuncion = new defun(null, null, null);
        String[] tokens = Instrucciones.split("\\s+");
        
        for(int i = 0; 1 < tokens.length; i++){

            if (tokens[i].equals("(defun") && i + 1 < tokens.length) {

                StringBuilder functionNameBuilder = new StringBuilder();
                functionNameBuilder.append(tokens[i + 1]);

                StringBuilder parameterBuilder = new StringBuilder();

                if(tokens[i].equals("(")){

                    int j = i + 2;

                    while (!tokens[j].equals(")") && j < tokens.length) {

                        parameterBuilder.append(j);
                        j++;

                    }
                    
                    
                    this.defunname = functionNameBuilder.toString();
                    this.Parametros = parameterBuilder.toString();
                    break;
                }
            }
        }
        return "";
    }
    // public String Operar(String Instrucciones){
    //     String[] tokens = Instrucciones.split("\\s+");

    //     for( String token : tokens){
    //         if(isNumeric(token)){
    //             stack.push(Integer.valueOf(token));
    //         } else {
    //             performOperation(token);
    //         }
    //     }

        // List<String> tokensList = new ArrayList<>(); 
        // for(String linea: Lineas){
        //     String[] tokens = linea.split("\\s+");
        //     for (String token : tokens) {
        //         tokensList.add(token);
        //     }
        // }                                                                           //Agarramos todas las lineas del archivo y las transforma a String, donde luego las dividimos por sus caracteres
        // boolean dentroParentesis = false;
        // List<String> palabrasEntreParentesis = new ArrayList<>();

        // for(String caracteres : tokensList) {
        //     if (caracteres.equals("(")) {
        //         dentroParentesis = true;
        //     } else if (caracteres.equals(")")) {
        //         dentroParentesis = false;
        //     } else if (dentroParentesis) {
        //         palabrasEntreParentesis.add(caracteres);
        //     }
        // }
    //     return "";

    // }


    public String getfunname(){
        return this.defunname;
    }

    public String getParametros(){
        return this.Parametros;
    }
    
    public String[] getInstrucciones(){
        return this.Instrucciones;
    }

    
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
