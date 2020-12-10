package biblioteca.arquivo;

import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.util.ArrayList;


/* @author Luam */

public class TesteLivroAutorEstante {
    
    public static void main(String[] args){
    
    /*  Teste da Função Leitura */
       
    ArrayList<Autor> autores = new ArrayList();
    autores = Leitor.leitorAutores("src\\unidades\\un1\\");
    for (Autor autor: autores){
       System.out.println(autor.getNome()+ " - " + autor.getPais());
    }
    
    Acervo acervo = new Acervo();
    acervo = Leitor.leitorEstantes("src\\unidades\\un1\\");
    
    Leitor.leitorLivros(acervo,"src\\unidades\\un1\\");
    acervo.imprimirAcervo("Teste");
    
    System.out.println("-----------------------------------------------");
    
    /*  Teste da Função Editar 
    Autor aluisio = new Autor("Aluísio Azevedo","Brasil");
    Livro livroEmprestado = new Livro(2, aluisio, "O cortiço","5229885", 518, "Clássico", "Cia das Letras", false);
    Editor.modificarEmprestimo(acervo, livroEmprestado, "src\\unidades\\un1\\","true");
    */
    
    /*  Teste da Função Editar */
    Autor rr = new Autor("Rick Riordan","Estados Unidos");
    Livro livroDeletado = new Livro(rr, "O Sangue do Olimpo","8452004", 650, "Mitologia", "Intrinseca", false);
    Removedor.removerLivro(livroDeletado,"src\\unidades\\un1\\");
    
    
    /*  Teste da Função Escrita 
    
    Escritor.escreverAutor(new Autor("Leon Toistoi", "Russia"), "src\\unidades\\un1\\");
    Escritor.escreverEstante(new Estante(55, "Literatura Russa"), "src\\unidades\\un1\\");
    Autor lucy = new Autor("Lucy Maud Montgomery","Canada");
    Escritor.escreveLivro(new Livro(90,lucy, "Lucy Maud Montgomery","1234",120,"Infantil","Abril",false), "src\\unidades\\un1\\");
    
    */
    
    
  
    }
}