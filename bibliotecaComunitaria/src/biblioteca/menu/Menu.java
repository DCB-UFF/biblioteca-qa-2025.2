package biblioteca.menu;

import biblioteca.biblioteca.Sistema;
import biblioteca.biblioteca.Unidade;
import biblioteca.livros.Livro;
import static java.lang.System.exit;
import java.util.ArrayList;
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
                        case 1: // ACERVO
                            teclado.nextLine();
                            MenuAcervo.opcoesAcervo();
                            while(op != 3){
                                op = teclado.nextInt();
                                teclado.nextLine();
                                
                                switch (op) {
                                    case 1: // CONSULTAR ACERVO
                                            MenuAcervo.opcoesConsultarAcervo();
                                            op = teclado.nextInt(); //CHECAR ERRO NO SWITCH CARALHOOOOO
                                            teclado.nextLine();
                                            while(op != 5){
                                                op = teclado.nextInt();
                                                switch (op) {
                                                    case 1:
                                                        System.out.println("\nDigite o título do livro: ");
                                                        teclado.nextLine();
                                                        String livro = teclado.nextLine();
                                                        Livro buscado = unidadeAtual.getAcervo().buscarLivroTitulo(livro);
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
                                                        unidadeAtual.getAcervo().imprimirAcervo(unidade);
                                                        exit(0);
                                                    case 4:
                                                        imprimir(unidadeAtual.getAcervo().getEmprestimos());
                                                        exit(0);
                                                        break;
                                                    case 5:
                                                        exit(0);
                                                        break;
                                                    default:
                                                        break;
                                                    } 
                                                    break;
                                                }

                                        case 2: //EDITAR ACERVO
                                            exit(0);
                                              break;

                                        case 3: //SAIR
                                            exit(0);
                                              break;
                                        default:
                                               break;
                                    }
                            }
 
                        case 2: // EMPRESTIMO
                            Inicial.adicionarFuncionario(unidadeAtual);
                            break;
                        case 3: // ADMINISTRACAO
                            Inicial.adicionarAutor(unidadeAtual);
                            break;
                        case 4:
                            exit(0);
                            break;
                        default:
                            break;
                    }
                }
            }
            else if(op == 2){ // CRIAR UNIDADE
                Inicial.opcoesAcessar();
            }
        }
        
    }
    
     public static void imprimir(ArrayList a){
        for (Object i: a){
           System.out.println(i);
        }
    }
}
