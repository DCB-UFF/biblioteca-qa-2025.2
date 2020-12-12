package biblioteca.menu;

import biblioteca.arquivo.Escritor;
import biblioteca.biblioteca.Unidade;
import biblioteca.biblioteca.Util;
import biblioteca.excecoes.FuncionarioInexistenteException;
import biblioteca.pessoas.Funcionario;
import static java.lang.System.exit;
import java.util.Scanner;

/* @author victoria */

public class MenuFuncionario {

    public static void opcoesAcessarAdminFuncionario(){
        
        System.out.println("\n1 - Buscar funcionario");
        System.out.println("2 - Adicionar funcionario");
        System.out.println("3 - Remover funcionario");
        System.out.println("4 - imprimir quadro de funcionarios\n");
        
    }
    
    public static void adicionarFuncionario(Unidade aux){
        
        Scanner teclado = new Scanner(System.in);
        Scanner tecla = new Scanner(System.in);
        
        System.out.println("\nDigite o nome do funcionário: ");
        String nome = tecla.nextLine();
        System.out.println("\nDigite o cpf do cliente: ");
        String cpf = tecla.nextLine();
        System.out.println("\nDigite o nascimento do funcionário: ");
        String data = tecla.nextLine();
        System.out.println("\nDigite o telefone do funcionário: ");
        String tele = tecla.nextLine();
        System.out.println("\nDigite o salário do funcionário: ");
        float sala = teclado.nextFloat();
        System.out.println("\nDigite o cargo do funcionário: ");
        String cargo = tecla.nextLine();
        System.out.println("\nDigite a rua do funcionário: ");
        String rua = tecla.nextLine();
        System.out.println("\nDigite o bairro do funcionário: ");
        String bairro = tecla.nextLine();
        System.out.println("\nDigite o cep do funcionário: ");
        String cep = tecla.nextLine();
        System.out.println("\nDigite a cidade do funcionário: ");
        String cid = tecla.nextLine();
        System.out.println("\nDigite o estado do funcionário: ");
        String est = tecla.nextLine();
                        
        Funcionario fun = new Funcionario(nome, cpf, data, tele, sala, cargo, rua, bairro, cep, cid, est);
        aux.getFuncionarios().add(fun);
        Escritor.escreverFuncionario(fun, aux.getPath());
        
    }
    
    public static void iniciar(Unidade unidadeAtual, Scanner teclado) throws FuncionarioInexistenteException{    
        opcoesAcessarAdminFuncionario();
        int op = teclado.nextInt();
        teclado.nextLine();
        while(op != 5){
                switch(op){
                    case 1:
                        System.out.println("Digite o cpf do funcionário: ");
                        String cpf = teclado.nextLine();
                        Util.buscarFuncionario(unidadeAtual, cpf);
                        exit(0);
                        break;
                    case 2:
                        adicionarFuncionario(unidadeAtual);
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
    }
}