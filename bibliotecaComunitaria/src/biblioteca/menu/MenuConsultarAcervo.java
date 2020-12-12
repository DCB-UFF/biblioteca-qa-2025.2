package biblioteca.menu;

import biblioteca.biblioteca.Unidade;
import biblioteca.biblioteca.Util;
import biblioteca.excecoes.*;
import biblioteca.livros.Livro;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Scanner;

/*@author Luam */



public class MenuConsultarAcervo {
    public static void opcoesConsultarAcervo(){
        System.out.println("Escolha o que você deseja fazer:");
        System.out.println("1 - Buscar livro pelo título");
        System.out.println("2 - Buscar livro pelo autor");
        System.out.println("3 - Imprimir acervo");
        System.out.println("4 - Imprimir livros emprestados");
        System.out.println("5 - Sair\n");
    } 
    
    public static void iniciar(Unidade unidadeAtual, Scanner teclado) throws LivroNaoExistenteException{
        int op=0;
        opcoesConsultarAcervo();
        while(op != 5){
            op = teclado.nextInt(); 
            switch (op) {
                case 1:
                    System.out.println("\nDigite o título do livro: ");
                    teclado.nextLine();
                    String livro = teclado.nextLine();
                    Livro buscado = Util.buscarLivroTitulo(livro, unidadeAtual);
                    buscado.imprimirLivro();
                    exit(0);

                    break;
                case 2:
                    System.out.println("\nDigite o nome do autor:");
                    teclado.nextLine();
                    String autor = teclado.nextLine();
                    ArrayList<Livro> achados = unidadeAtual.getAcervo().buscarLivroAutor(autor);
                    for (Livro l: achados){
                        l.imprimirLivro();
                    }
                    exit(0);
                case 3:
                    unidadeAtual.getAcervo().imprimirAcervo(unidadeAtual.getNome());
                    exit(0);
                case 4:
                    Util.imprimir(unidadeAtual.getAcervo().getEmprestimos());
                    exit(0);
                    break;
                case 5:
                    exit(0);
                    break;
                default:
                    break;
                } 
            }
    }
}
