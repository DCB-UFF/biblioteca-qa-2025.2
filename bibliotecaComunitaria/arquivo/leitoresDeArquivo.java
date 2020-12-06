package biblioteca.arquivo;

import biblioteca.livros.Acervo;
import biblioteca.livros.Livro;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* @author Luam */
public class leitoresDeArquivo {
     public static Acervo leitorLivros() {
        BufferedReader br = null;
        String linha = "";
        ArrayList<Livro> Livros = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("acervo.csv"));
            br.readLine();
            
            while ((linha = br.readLine()) != null) {
                // A cada linha lê-se um aluno
                String[] livro = linha.split(",");
               
                
            }
            return Acervo;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }   
        }
        return null;
      }
}
