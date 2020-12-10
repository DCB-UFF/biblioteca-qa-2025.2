package biblioteca.menu;

import biblioteca.biblioteca.Unidade;
import biblioteca.livros.Emprestimo;
import static java.lang.System.exit;
import java.util.Scanner;

/* @author Luam */
public class MenuEmprestimo {
    public static void opcoesEmprestar(){
        System.out.println("Escolha o que você deseja fazer:");
        System.out.println("1 - Emprestar um livro");
        System.out.println("2 - Devolver um livro");
        System.out.println("3 - Sair\n");
    } 
    public static void iniciar(Unidade unidadeAtual, Scanner teclado){
        int op=0;
        opcoesEmprestar();
        while(op != 3){
            op = teclado.nextInt(); 
            teclado.nextLine();
            switch (op) {
                case 1:
                    System.out.println("\nDigite o cpf do Cliente: ");
                    String cpf = teclado.nextLine();
                    System.out.println("\nDigite o ISBN do livro ");
                    String isbn = teclado.nextLine();
                    System.out.println("\nDigite a data de Emprestimo: ");
                    String emp = teclado.nextLine();
                    System.out.println("\nDigite a data de Devolução: ");
                    String dev = teclado.nextLine();

                    Emprestimo novo = new Emprestimo(cpf,isbn,emp,dev);
                    unidadeAtual.getAcervo().emprestarLivro(unidadeAtual, novo);
                    
                    exit(0);

                    break;
                case 2:
                    System.out.println("\nDigite o cpf do Cliente: ");
                    String cpf2 = teclado.nextLine();
                    System.out.println("\nDigite o ISBN do livro ");
                    String isbn2 = teclado.nextLine();
                    unidadeAtual.getAcervo().devolverLivro(unidadeAtual, cpf2, isbn2);

                    exit(0);
                    
                case 3:
                    exit(0);
                default:
                    break;
                } 
            }
    }
}
   