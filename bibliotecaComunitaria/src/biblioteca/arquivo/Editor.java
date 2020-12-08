package biblioteca.arquivo;

import biblioteca.livros.*;
import java.io.*;


/* @author Luam */
public class Editor {
     public static void modificarEmprestimo(Acervo acervo, Livro livroEmprestado, String path, String booleano) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File antigo = new File(path+ "livros.csv");
        File novo = new File (path+ "temp.csv");
        
        try {
            br = new BufferedReader(new FileReader(antigo));
            bw = new BufferedWriter(new FileWriter(novo, true));
            PrintWriter pw= new PrintWriter(bw);
            String linha = "";
            String linhaEditada="";
            
           
            while ((linha = br.readLine()) != null) {
    
                String[] livro = linha.split(",");
                if (livro[1].equals(livroEmprestado.getTitulo())){
                    livro[6]= booleano;
                    linhaEditada = livro[0] + "," + livro[1] + "," + livro[2] + "," +
                livro[3] + "," + livro[4]  + "," + livro[5] + "," + 
                livro[6]+ "," + livro[7];
                }
                else{
                    linhaEditada = linha;
                }

                pw.println(linhaEditada);
            }
            pw.flush();  
            pw.close();
            br.close();
            antigo.delete();
            
            
            File aux = new File (path+"livros.csv");
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
