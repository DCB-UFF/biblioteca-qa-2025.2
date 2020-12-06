package biblioteca.arquivo;

import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/* @author Luam */
public class Editor {
     public static void emprestar(Acervo acervo, Livro livroEmprestado) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File antigo = new File("livros.csv");
        File novo = new File ("temp.csv");
        
        try {
            br = new BufferedReader(new FileReader(antigo));
            bw = new BufferedWriter(new FileWriter(novo, true));
            PrintWriter pw= new PrintWriter(bw);
            String linha = "";
            String linhaEditada="";
            
           
            while ((linha = br.readLine()) != null) {
    
                String[] livro = linha.split(",");
                if (livro[1].equals(livroEmprestado.getTitulo())){
                    livro[6]= "true";
                    linhaEditada = livro[0] + "," + livro[1] + "," + livro[2] + "," +
                livro[3] + "," + livro[4]  + "," + livro[5] + "," + 
                livro[6]+ "," + livro[7];
                }
                else{
                    linhaEditada = linha;
                }

                pw.println(linhaEditada);
                pw.flush();      
                
            }
            pw.close();
            antigo.delete();
            br.close();
            
            File aux = new File ("livros.csv");
            novo.renameTo(aux);
            
            
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
