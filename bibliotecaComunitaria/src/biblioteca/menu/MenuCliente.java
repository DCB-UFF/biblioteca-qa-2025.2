package biblioteca.menu;

import biblioteca.biblioteca.*;
import biblioteca.excecoes.ClienteInexistenteException;
import biblioteca.pessoas.Cliente;
import static java.lang.System.exit;
import java.util.Scanner;

/* @author victoria */
public class MenuCliente {


    private final ExitHandler exitHandler;


    public MenuCliente(ExitHandler exitHandler) {
        this.exitHandler = exitHandler;
    }

    public static void opcoesAcessarAdminCliente(){

        System.out.println("\n1 - Buscar cliente");
        System.out.println("2 - Adicionar cliente");
        System.out.println("3 - Remover cliente");
        System.out.println("4 - imprimir quadro de clientes");
        System.out.println("5 - Sair\n");

    }

    public void adicionarCliente(Unidade aux){

        Scanner tecla = new Scanner(System.in);

        System.out.println("\nDigite o nome do cliente: ");
        String nome = tecla.nextLine();
        System.out.println("\nDigite o cpf do cliente: ");
        String cpf = tecla.nextLine();
        System.out.println("\nDigite o nascimento do cliente: ");
        String data = tecla.nextLine();
        System.out.println("\nDigite o telefone do cliente: ");
        String tele = tecla.nextLine();
        System.out.println("\nDigite a rua do cliente: ");
        String rua = tecla.nextLine();
        System.out.println("\nDigite o bairro do cliente: ");
        String bairro = tecla.nextLine();
        System.out.println("\nDigite o cep do cliente: ");
        String cep = tecla.nextLine();
        System.out.println("\nDigite a cidade do cliente: ");
        String cid = tecla.nextLine();
        System.out.println("\nDigite o estado do cliente: ");
        String est = tecla.nextLine();

        Cliente cliente = new Cliente(nome, cpf, data, tele, rua, bairro, cep, cid, est);
        aux.getClientes().add(cliente);
        Cliente.escreverCliente(cliente, aux.getPath());

        exitHandler.exit(0);
    }


    public void removerCliente(Unidade unidadeAtual) throws ClienteInexistenteException{
        Scanner tecla = new Scanner(System.in);
        System.out.println("\nDigite o cpf do cliente: ");
        String cpf = tecla.nextLine();

        Cliente buscado = Util.buscarCliente(unidadeAtual, cpf);
        unidadeAtual.getClientes().remove(buscado);
        Cliente.removerCliente(buscado, unidadeAtual.getPath());
        System.out.println("O cliente de cpf "+cpf+"foi removido!");

        exitHandler.exit(0);

    }

    public void iniciar(Unidade unidadeAtual, Scanner teclado) throws ClienteInexistenteException {
        opcoesAcessarAdminCliente();
        int op = teclado.nextInt();
        teclado.nextLine();

        while (true) {
            switch (op) {
                case 1:
                    System.out.println("Digite o cpf do cliente: ");
                    String cpf = teclado.nextLine();
                    if (cpf.length() != 11) {
                        System.out.println("CPF inválido!");
                    } else {
                        Cliente buscado = Util.buscarCliente(unidadeAtual, cpf);
                        if (buscado == null) {
                            System.out.println("Cliente não encontrado!");
                        } else {
                            System.out.println(buscado);
                        }
                    }
                    exitHandler.exit(0);
                    break;
                case 2:
                    adicionarCliente(unidadeAtual);
                    break;
                case 3:
                    removerCliente(unidadeAtual);
                    break;
                case 4:
                    if (unidadeAtual.getClientes().isEmpty()) {
                        System.out.println("Nenhum cliente cadastrado.");
                    } else {
                        for (Cliente c : unidadeAtual.getClientes()) {
                            System.out.println(c);
                        }
                    }
                    exitHandler.exit(0);
                    break;
                case 5:
                    System.out.println("Fechando aplicação...");
                    exitHandler.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }

            op = teclado.nextInt();
            teclado.nextLine();
        }
    }
}