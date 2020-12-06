package biblioteca.arquivo;

import biblioteca.livros.Acervo;
import biblioteca.livros.Livro;
import java.io.*;

/* @author Luam */

public class Removedor {
    
     public static void remover(Acervo acervo, Livro livroEmprestado) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File antigo = new File("livros.csv");
        File novo = new File ("temp.csv");
        
        try {
            br = new BufferedReader(new FileReader(antigo));
            bw = new BufferedWriter(new FileWriter(novo, true));
            PrintWriter pw= new PrintWriter(bw);
            String linha = "";
            
            while ((linha = br.readLine()) != null) {
    
                String[] livro = linha.split(",");
                if (!livro[1].equals(livroEmprestado.getTitulo())){
                    pw.println(linha);
                }
            }
            pw.flush();  
            pw.close();
            br.close();
            antigo.delete();
            
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