package biblioteca.menu;

import biblioteca.livros.Livro;
import static java.lang.System.exit;
import java.util.Scanner;

/* @author Luam */
public class MenuAcervo {
   public static void opcoesAcervo(){
        System.out.println("Escolha o que você deseja fazer:");
        System.out.println("\n1 - Consultar acervo");
        System.out.println("2 - Editar acervo");
        System.out.println("3 - Sair\n");
    } 
    public static void opcoesConsultarAcervo(){
        System.out.println("Escolha o que você deseja fazer:");
        System.out.println("\n1 - Buscar livro pelo título");
        System.out.println("2 - Buscar livro pelo autor");
        System.out.println("3 - Imprimir acervo");
        System.out.println("4 - Imprimir livros emprestados");
        System.out.println("5 - Sair\n");
    }  
}
