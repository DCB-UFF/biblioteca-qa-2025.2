package biblioteca.arquivo;

import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.util.ArrayList;


/* @author Luam */

public class TestesArquivo {
    
    public static void main(String[] args){
    ArrayList<Autor> autores = new ArrayList();
    
    autores = leitoresDeArquivo.leitorAutores();
    
    for (Autor autor: autores){
       System.out.println(autor.getNome()+ " - " + autor.getPais());
    }
    
    Acervo acervo = new Acervo();
    acervo = leitoresDeArquivo.leitorEstantes();
    
    leitoresDeArquivo.leitorLivros(acervo);
    acervo.imprimirAcervo();
    
    Escritor.escreverAutor(new Autor("Meg", "Estados Unidos"));
    
    /*ArrayList<Estante> estantes = new ArrayList();
    estantes = leitoresDeArquivo.leitorEstantes();
    
    for (Estante estante: estantes){
        System.out.println(estante.getIdEstante() + " - " + estante.getGenero() + " -  " + estante.getCapacidade());
    }*/
    
    }
}