package biblioteca.arquivo;

import biblioteca.pessoas.Autor;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/* @author Luam */

public class Escritor {
    public static void escreverAutor(Autor autor) {
           BufferedWriter bw = null;
           String linha = autor.getNome()+","+autor.getPais();
           try {
               bw = new BufferedWriter(new FileWriter("autores.csv", true));
               PrintWriter pw= new PrintWriter(bw);
               pw.println(linha);
               pw.flush();
               pw.close();
       

           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               if (bw != null) {
                   try {
                       bw.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }   
           }
    }
    
}
