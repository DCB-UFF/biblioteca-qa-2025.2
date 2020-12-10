package biblioteca.menu;

import biblioteca.biblioteca.Sistema;
import biblioteca.biblioteca.Unidade;
import biblioteca.livros.Livro;
import biblioteca.pessoas.Cliente;
import biblioteca.pessoas.Funcionario;
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
                            System.out.println("Escolha o que você deseja fazer:");
                            System.out.println("1 - Consultar acervo\n2 - Editar acervo\n3 - Sair\n");
                            while(op != 3){
                                op = teclado.nextInt();
                                teclado.nextLine();
                                switch (op) {
                                    case 1: // CONSULTAR ACERVO
                                        ConsultorAcervo.gerar(unidadeAtual, teclado);
                                        break;
                                    case 2: //EDITAR ACERVO
                                        EditorAcervo.gerar(unidadeAtual, teclado);
                                        break;
                                    case 3: //SAIR
                                        exit(0);
                                        break;
                                    default:
                                        break;
                                    }
                            }
 
                        case 2: // EMPRESTIMO
                            Emprestador.gerar(unidadeAtual, teclado);
                            break;
                        case 3: // ADMINISTRACAO
                            Inicial.opcoesAcessarAdmin();
                            op = teclado.nextInt();
                            teclado.nextLine();
                            while(op != 3){
                                switch(op){
                                    case 1:
                                        Inicial.opcoesAcessarAdminCliente();
                                        op = teclado.nextInt();
                                        teclado.nextLine();
                                        while(op != 5){
                                            switch(op){
                                                case 1:
                                                    System.out.println("Digite o cpf do cliente: ");
                                                    String cpf = teclado.nextLine();
                                                    for(Cliente c : unidadeAtual.getClientes()){
                                                        if(c.getCPF().equals(cpf)){
                                                            System.out.println(c);
                                                        }
                                                    }
                                                    exit(0);
                                                    break;
                                                case 2:
                                                    Inicial.adicionarCliente(unidadeAtual);
                                                    exit(0);
                                                    break;
                                                case 3:
                                                    exit(0);
                                                    break;
                                                case 4:
                                                    for(Cliente c : unidadeAtual.getClientes()){
                                                        System.out.println(c);
                                                    }
                                                    exit(0);
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    break;
                                case 2:
                                    Inicial.opcoesAcessarAdminFuncionario();
                                    op = teclado.nextInt();
                                    teclado.nextLine();
                                    while(op != 5){
                                            switch(op){
                                                case 1:
                                                    System.out.println("Digite o cpf do funcionário: ");
                                                    String cpf = teclado.nextLine();
                                                    /*for(Funcionario f : unidadeAtual.getFuncionarios()){
                                                        if(f.getCPF().equals(cpf)){
                                                            System.out.println(f);
                                                        }
                                                    }*/
                                                    exit(0);
                                                    break;
                                                case 2:
                                                    Inicial.adicionarFuncionario(unidadeAtual);
                                                    exit(0);
                                                    break;
                                                case 3:
                                                    exit(0);
                                                    break;
                                                case 4:
                                                    for(Funcionario f : unidadeAtual.getFuncionarios()){
                                                        System.out.println(f);
                                                    }
                                                    exit(0);
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
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
                MenuCriadorUnidades.gerar(sistema, teclado);
                exit(0);
            }
        }
    }
    
     public static void imprimir(ArrayList a){
        for (Object i: a){
           System.out.println(i);
        }
    }
}
