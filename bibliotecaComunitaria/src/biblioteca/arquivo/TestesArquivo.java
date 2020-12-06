package biblioteca.arquivo;

import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.util.ArrayList;


/* @author Luam */

public class TestesArquivo {
    
    public static void main(String[] args){
    
    /*  Teste da Função Escrita */
       
    ArrayList<Autor> autores = new ArrayList();
    autores = Leitor.leitorAutores();
    for (Autor autor: autores){
       System.out.println(autor.getNome()+ " - " + autor.getPais());
    }
    
    Acervo acervo = new Acervo();
    acervo = Leitor.leitorEstantes();
    
    Leitor.leitorLivros(acervo);
    acervo.imprimirAcervo();
    
    System.out.println("-----------------------------------------------");
    
    /*  Teste da Função Editar */
    Autor aluisio = new Autor("Aluísio Azevedo","Brasil");
    Livro livroEmprestado = new Livro(2, aluisio, "O cortiço","5229885", 518, "Clássico", "Cia das Letras", false);
    Editor.emprestar(acervo, livroEmprestado);
    
    
    /*  Teste da Função Escrita */
    
    //Escritor.escreverAutor(new Autor("Leon Toistoi", "Russia"));
    //Escritor.escreveEstante(new Estante(55, "Literatura Russa"));
    //Autor lucy = new Autor("Lucy Maud Montgomery","Canada");
    //Escritor.escreveLivro(new Livro(90,lucy, "Lucy Maud Montgomery","1234",120,"Infantil","Abril",false));
    
    
    
    
  
    }
}