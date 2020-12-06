package biblioteca.arquivo;

import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.util.ArrayList;


/* @author Luam */

public class TestesArquivo {
    
    public static void main(String[] args){
    ArrayList<Autor> autores = new ArrayList();
    
    autores = Leitor.leitorAutores();
    
    for (Autor autor: autores){
       System.out.println(autor.getNome()+ " - " + autor.getPais());
    }
    
    Acervo acervo = new Acervo();
    acervo = Leitor.leitorEstantes();
    
    Leitor.leitorLivros(acervo);
    acervo.imprimirAcervo();
    
    Escritor.escreverAutor(new Autor("Leon Toistoi", "Russia"));
    Escritor.escreveEstante(new Estante(55, "Literatura Russa"));
    Autor lucy = new Autor("Lucy Maud Montgomery","Canada");
    Escritor.escreveLivro(new Livro(90,lucy, "Lucy Maud Montgomery","1234",120,"Infantil","Abril",false));
    
    
    /*ArrayList<Estante> estantes = new ArrayList();
    estantes = Leitor.leitorEstantes();
    
    for (Estante estante: estantes){
        System.out.println(estante.getIdEstante() + " - " + estante.getGenero() + " -  " + estante.getCapacidade());
    }*/
    
    }
}