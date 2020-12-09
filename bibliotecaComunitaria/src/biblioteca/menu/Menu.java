package biblioteca.menu;

import biblioteca.biblioteca.Sistema;
import biblioteca.biblioteca.Unidade;
import biblioteca.livros.Livro;
import static java.lang.System.exit;
import java.util.Scanner;

/*@author victoria */

public class Menu {

    public static void chamada(Sistema sistema){
        
        System.out.println("Seja bem-vindo(a) ao Sistema de Bibliotecas Comunitárias!\n" +
                            "Escolha o que deseja fazer:");
        Inicial.principal();
        Scanner teclado = new Scanner(System.in);
        int op = teclado.nextInt();
        Unidade unidadeAtual = null;
        
        while(op != 3){
            if(op == 1){
                Inicial.imprimir(Main.sistema.unidades);
                System.out.println("\nDigite o nome da unidade: ");
                
                teclado.nextLine();
                String unidade = teclado.nextLine();
                unidadeAtual = sistema.buscarUnidade(unidade);
                
                System.out.println("\nBem vindo(a) à unidade " + unidadeAtual.getNome()+"!");
                System.out.println("Escolha o que você deseja acessar:");
                Inicial.opcoesAcessar();
                
                op = teclado.nextInt();
                while(op != 5){
                    switch (op) {
                        case 1:
                            MenuAcervo.opcoesAcervo();
                            switch (op) {
                        case 1:
                            ;
                            
                            break;
                        case 2:
                            Inicial.adicionarFuncionario(unidadeAtual);
                            break;
                        case 3:
                            Inicial.adicionarAutor(unidadeAtual);
                            break;
                        case 4:
                            Inicial.adicionarCliente(unidadeAtual);
                            break;
                        default:
                            break;
                    }
                            break;
                        case 2:
                            Inicial.adicionarFuncionario(unidadeAtual);
                            break;
                        case 3:
                            Inicial.adicionarAutor(unidadeAtual);
                            break;
                        case 4:
                            Inicial.adicionarCliente(unidadeAtual);
                            break;
                        default:
                            break;
                    }
                }
            }
            else if(op == 2){
                Inicial.opcoesAcessar();
            }
        }
        
    }
}
