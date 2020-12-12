package biblioteca.menu;

import biblioteca.biblioteca.*;
import biblioteca.excecoes.*;
import static java.lang.System.exit;
import java.util.Scanner;

/*@author victoria */

public class Menu {

    public static void iniciar(Sistema sistema) throws LivroNaoExistenteException, ClienteInexistenteException{
        MenuPrincipal.opcoesIniciais();
        Unidade unidadeAtual;
        
        Scanner teclado = new Scanner(System.in);
        int op = teclado.nextInt();
        while(op != 3){
            if(op == 1){
                sistema.imprimirUnidades(sistema);
                System.out.println("\nDigite o nome da unidade: ");
                
                teclado.nextLine();
                String unidade = teclado.nextLine();
                unidadeAtual = sistema.buscarUnidade(unidade);
                
                System.out.println("\nBem vindo(a) à unidade " + unidadeAtual.getNome()+"!");
                MenuPrincipal.opcoesAcessar();
                
                op = teclado.nextInt();
                while(op != 5){
                    switch (op) {
                        case 1: // ACERVO
                            teclado.nextLine();
                            MenuPrincipal.opcoesAcessarAcervo();
                            while(op != 3){
                                op = teclado.nextInt();
                                teclado.nextLine();
                                switch (op) {
                                    case 1: // CONSULTAR ACERVO
                                        MenuConsultarAcervo.iniciar(unidadeAtual, teclado);
                                        break;
                                    case 2: //EDITAR ACERVO
                                        MenuEditarAcervo.iniciar(unidadeAtual, teclado);
                                        break;
                                    case 3: //SAIR
                                        exit(0);
                                        break;
                                    default:
                                        break;
                                    }
                            }
 
                        case 2: // EMPRESTIMO
                            MenuEmprestimo.iniciar(unidadeAtual, teclado);
                            break;
                        case 3: // ADMINISTRACAO
                            MenuPrincipal.opcoesAcessarAdmin();
                            op = teclado.nextInt();
                            teclado.nextLine();
                            while(op != 3){
                                switch(op){
                                    case 1:
                                        MenuCliente.iniciar(unidadeAtual, teclado);
                                    break;
                                case 2:
                                        MenuFuncionario.iniciar(unidadeAtual, teclado);
                                    break;
                                case 3:
                                    exit(0);
                                    break;
                                default:
                                    break;
                                }
                            }
                        case 4:
                            exit(0);
                            break;
                        default:
                            break;
                    }
                }
            }
            else if(op == 2){ // CRIAR UNIDADE
                MenuCriarNovaUnidade.iniciar(sistema, teclado);
                exit(0);
            }
        }
    }    
}
