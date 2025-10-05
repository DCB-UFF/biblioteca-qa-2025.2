package biblioteca.menu;

import biblioteca.biblioteca.*;
import biblioteca.excecoes.ClienteInexistenteException;
import biblioteca.excecoes.FuncionarioInexistenteException;
import biblioteca.pessoas.Cliente;
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
        Funcionario.escreverFuncionario(fun, aux.getPath());
        
    }
    
     public static void removerFuncionario(Unidade unidadeAtual) throws FuncionarioInexistenteException{
        Scanner tecla = new Scanner(System.in);
        System.out.println("\nDigite o cpf do funcionario: ");
        String cpf = tecla.nextLine();
        
        Funcionario buscado = Util.buscarFuncionario(unidadeAtual, cpf);
        unidadeAtual.getFuncionarios().remove(buscado);
        Funcionario.removerFuncionario(buscado, unidadeAtual.getPath());
        System.out.println("O funcionario de cpf "+cpf+"foi removido!");

    }

    public static void iniciar(Unidade unidadeAtual, Scanner teclado) throws FuncionarioInexistenteException {
        int op;
        int tentativas = 0;
        boolean continuar = true;

        while (continuar) {
            opcoesAcessarAdminFuncionario();
            op = teclado.nextInt();
            teclado.nextLine();

            if (op == 1) {
                System.out.println("Digite o cpf do funcionário: ");
                String cpf = teclado.nextLine();
                Funcionario buscado = Util.buscarFuncionario(unidadeAtual, cpf);
                if (buscado != null) {
                    System.out.println(buscado);
                } else {
                    System.out.println("Funcionário não encontrado.");
                }
                tentativas++;
            } else if (op == 2) {
                adicionarFuncionario(unidadeAtual);
                tentativas++;
            } else if (op == 3) {
                removerFuncionario(unidadeAtual);
                tentativas++;
            } else if (op == 4) {
                if (unidadeAtual.getFuncionarios().isEmpty()) {
                    System.out.println("Nenhum funcionário cadastrado.");
                } else {
                    for (Funcionario f : unidadeAtual.getFuncionarios()) {
                        System.out.println(f);
                    }
                }
                tentativas++;
            } else if (op == 5) {
                System.out.println("Fechando aplicação...");
                continuar = false;
            } else {
                System.out.println("Opção inválida.");
                tentativas++;
            }

            if (tentativas >= 5) {
                System.out.println("Você já realizou 5 operações.");
            }
        }
    }

}