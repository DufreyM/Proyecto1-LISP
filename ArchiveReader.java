import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ArchiveReader {

    public void Interpretador(String FilePath){
        try (BufferedReader br = new BufferedReader(new FileReader(FilePath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] tokens = linea.split("\\s+");
                for (int i = 0; i < tokens.length; i++) {
                    String token = tokens[i];
                    if (token.equals("(defun")) {
                        System.out.println("Encontré defun");
    
                        // Buscar ' después de (defun
                        if(token.equals("'")){
                            System.out.println("Encontré '");
                        } else if(token.equals(")")){
                            System.out.println("Encontré )");
                        }
                        for (int j = i + 1; j < tokens.length; j++) {
                            if (tokens[j].equals("'")) {
                                System.out.println("Encontré ' después de defun en la posición: ");
                            } else if (tokens[j].equals(")")) {
                                System.out.println("Encontré ) después de defun en la posición: ");
                            }
                        }
                    } else if(token.equals("(setq")){
                        System.out.println("Encontré setq");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
