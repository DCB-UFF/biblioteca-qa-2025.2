package biblioteca.menu;

import biblioteca.biblioteca.*;
import biblioteca.excecoes.*;
import java.util.Scanner;

public class Menu {

    public static void iniciar(Sistema sistema) throws LivroNaoExistenteException, ClienteInexistenteException, FuncionarioInexistenteException, UnidadeInexistenteException {
        MenuPrincipal.opcoesIniciais();
        Unidade unidadeAtual;
        Scanner teclado = new Scanner(System.in);
        int op = 0, tentativas = 0;
        boolean continuar = true;

        while (continuar) {
            if (teclado.hasNextInt()) {
                op = teclado.nextInt();
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                teclado.nextLine();
                tentativas++;
                continue;
            }

            if (op == 1) {
                sistema.imprimirUnidades(sistema);
                System.out.println("\nDigite o nome da unidade: ");
                teclado.nextLine();
                String unidade = teclado.nextLine();
                unidadeAtual = Util.buscarUnidade(unidade, sistema);

                if (unidadeAtual == null) {
                    System.out.println("Unidade não encontrada.");
                    tentativas++;
                    continue;
                }

                System.out.println("\nBem vindo(a) à unidade " + unidadeAtual.getNome() + "!");
                MenuPrincipal.opcoesAcessar();

                int subOp = teclado.nextInt();
                teclado.nextLine();

                while (subOp != 5) {
                    if (subOp == 1) {
                        MenuPrincipal.opcoesAcessarAcervo();
                        int acervoOp = teclado.nextInt();
                        teclado.nextLine();

                        if (acervoOp == 1) {
                            MenuConsultarAcervo.iniciar(unidadeAtual, teclado);
                        } else if (acervoOp == 2) {
                            MenuEditarAcervo.iniciar(unidadeAtual, teclado);
                        } else if (acervoOp == 3) {
                            System.out.println("Saindo do acervo...");
                            break;
                        } else {
                            System.out.println("Opção de acervo inválida.");
                            tentativas++;
                        }
                        tentativas++;
                    } else if (subOp == 2) {
                        MenuEmprestimo.iniciar(unidadeAtual, teclado);
                        tentativas++;
                    } else if (subOp == 3) {
                        MenuPrincipal.opcoesAcessarAdmin();
                        int adminOp = teclado.nextInt();
                        teclado.nextLine();

                        if (adminOp == 1) {
                            MenuCliente.iniciar(unidadeAtual, teclado);
                        } else if (adminOp == 2) {
                            MenuFuncionario.iniciar(unidadeAtual, teclado);
                        } else if (adminOp == 3) {
                            System.out.println("Saindo da administração...");
                            break;
                        } else {
                            System.out.println("Opção de administração inválida.");
                            tentativas++;
                        }
                        tentativas++;
                    } else if (subOp == 4) {
                        System.out.println("Saindo da unidade...");
                        break;
                    } else {
                        System.out.println("Opção inválida.");
                        tentativas++;
                    }

                    if (tentativas >= 5) {
                        System.out.println("Você já realizou 5 operações.");
                    }
                    subOp = teclado.nextInt();
                    teclado.nextLine();
                }
                tentativas++;
            } else if (op == 2) {
                MenuCriarNovaUnidade.iniciar(sistema, teclado);
                tentativas++;
                System.out.println("Nova unidade criada.");
            } else if (op == 3) {
                System.out.println("Saindo do sistema...");
                continuar = false;
            } else {
                System.out.println("Opção inválida.");
                tentativas++;
            }

            if (tentativas == 4) {
                System.out.println("Considere revisar suas opções para melhor uso do sistema.");
            }

            if (tentativas == 7) {
                System.out.println("Fechando a aplicação");
                continuar = false;
            }
        }
    }
}

