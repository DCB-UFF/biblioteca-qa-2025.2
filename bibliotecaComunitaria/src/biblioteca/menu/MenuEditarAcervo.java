package biblioteca.menu;

import biblioteca.biblioteca.*;
import biblioteca.excecoes.LivroNaoExistenteException;
import biblioteca.livros.Livro;
import biblioteca.pessoas.Autor;
import static java.lang.System.exit;
import java.util.Scanner;

/* @author Luam */

public class MenuEditarAcervo {

    public static void opcoesEditarAcervo(){
        System.out.println("Escolha o que você deseja fazer:");
        System.out.println("1 - Adicionar livro");
        System.out.println("2 - Remover livro");
        System.out.println("3 - Sair\n");
    } 
    
    public static void iniciar(Unidade unidadeAtual, Scanner teclado) throws LivroNaoExistenteException{
        int op=0;
        opcoesEditarAcervo();
        while(op != 3){
            op = teclado.nextInt(); 
            teclado.nextLine();
            switch (op) {
                case 1:
                    System.out.println("\nDigite o nome do autor: ");
                    String nome = teclado.nextLine();
                    System.out.println("\nDigite o país do autor: ");
                    String pais = teclado.nextLine();
                    Autor autor = new Autor(nome, pais);
                    System.out.println("\nDigite o título do livro: ");
                    String titulo = teclado.nextLine();
                    System.out.println("\nDigite o isbn do livro: ");
                    String isbn = teclado.nextLine();
                    System.out.println("\nDigite a quantidade de páginas do livro: ");
                    int pag = teclado.nextInt();
                    teclado.nextLine();
                    System.out.println("\nDigite o gênero do livro: ");
                    String gene = teclado.nextLine();
                    System.out.println("\nDigite a editora do livro: ");
                    String edit = teclado.nextLine();
                    Livro novo = new Livro(autor, titulo, pag, isbn, gene, edit, false); 
                    teclado.nextLine();
                    
                    Livro adicionado = new Livro(autor, titulo,  pag,isbn, gene, edit, false);               
                    unidadeAtual.getAcervo().addLivro2(adicionado, unidadeAtual.getPath());
                    System.out.println("\nLivro adicionado!!");
                    
                    exit(0);

                    break;
                case 2:
                    System.out.println("\nDigite o título do arquivo que você deseja remover:");
                    String titulo2 = teclado.nextLine();
                    Livro removido = Util.buscarLivroTitulo(titulo2, unidadeAtual);
                    unidadeAtual.getAcervo().removeLivro(removido, unidadeAtual.getPath());
                    System.out.println("\nLivro removido!!");
                    exit(0);
                    
                case 3:
                    exit(0);
                default:
                    break;
                } 
            }
    }
}