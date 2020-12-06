package biblioteca.arquivo;

import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/* @author Luam */
public class Leitor {
     public static ArrayList<Autor> leitorAutores() {
        BufferedReader br = null;
        String linha = "";
        ArrayList<Autor> Autores = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("autores.csv"));
            br.readLine();
            
            while ((linha = br.readLine()) != null) {
                String[] autor = linha.split(",");
                Autor novo = new Autor();
                novo.cadastro(autor[0],autor[1]);
                Autores.add(novo);
                
            }
            return Autores;
            
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
     
     public static Acervo leitorEstantes() {
        BufferedReader br = null;
        String linha = "";
        Acervo acervo = new Acervo();
        try {
            br = new BufferedReader(new FileReader("estantes.csv"));
            br.readLine();
            
            while ((linha = br.readLine()) != null) {
                String[] estante = linha.split(",");
                Estante nova = new Estante(Integer.parseInt(estante[0]),estante[1]);
                acervo.addEstante(nova);
            }
            return acervo;
            
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
     
     
     public static void leitorLivros(Acervo acervo) {
        BufferedReader br = null;
        String linha = "";
        
        try {
            br = new BufferedReader(new FileReader("livros.csv"));
            br.readLine();
            
            while ((linha = br.readLine()) != null) {
                
                String[] livro = linha.split(",");
                Autor autor = new Autor(livro[0],livro[7]);
                Livro novo = new Livro(acervo.idsLivros++, autor,livro[1], 
                    livro[2], Integer.parseInt(livro[3]), livro[4],livro[5],Boolean.parseBoolean(livro[6]));
                acervo.addLivro(novo);
            
            }
                
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
      }
}
