package biblioteca.menu;

import biblioteca.biblioteca.*;
import biblioteca.excecoes.ClienteInexistenteException;
import biblioteca.pessoas.Cliente;
import java.util.Scanner;

public class MenuCliente {

    public static void opcoesAcessarAdminCliente() {
        System.out.println("1 - Buscar cliente");
        System.out.println("2 - Adicionar cliente");
        System.out.println("3 - Remover cliente");
        System.out.println("4 - Imprimir quadro de clientes");
        System.out.println("5 - Voltar ao menu da unidade\n");
    }

    public static void adicionarCliente(Unidade aux, Scanner tecla) {
        System.out.println("\n--- Adicionar Novo Cliente ---");
        System.out.println("Digite o nome do cliente: ");
        String nome = tecla.nextLine();
        System.out.println("Digite o cpf do cliente: ");
        String cpf = tecla.nextLine();
        System.out.println("Digite o nascimento do cliente (dd/mm/aaaa): ");
        String data = tecla.nextLine();
        System.out.println("Digite o telefone do cliente: ");
        String tele = tecla.nextLine();
        System.out.println("Digite a rua do cliente: ");
        String rua = tecla.nextLine();
        System.out.println("Digite o bairro do cliente: ");
        String bairro = tecla.nextLine();
        System.out.println("Digite o cep do cliente: ");
        String cep = tecla.nextLine();
        System.out.println("Digite a cidade do cliente: ");
        String cidade = tecla.nextLine();
        System.out.println("Digite o estado do cliente (UF): ");
        String estado = tecla.nextLine();

        Cliente cliente = new Cliente(nome, cpf, data, tele, rua, bairro, cep, cidade, estado);
        aux.getClientes().add(cliente);

        Cliente.escreverCliente(cliente, aux.getPath());
        System.out.println("Cliente adicionado com sucesso!");
    }

    public static void removerCliente(Unidade aux, Scanner tecla) throws ClienteInexistenteException {
        System.out.println("\nDigite o cpf do cliente a ser removido: ");
        String cpf = tecla.nextLine();

        if (cpf.isBlank()) {
            System.out.println("CPF inválido.");
            return;
        }

        Cliente clienteDeletado = Util.buscarCliente(aux, cpf);
        aux.getClientes().remove(clienteDeletado);
        Cliente.removerCliente(clienteDeletado, aux.getPath());
    }

    public static void iniciar(Unidade unidadeAtual, Scanner teclado) {
        boolean continuar = true;
        while (continuar) {
            opcoesAcessarAdminCliente();
            int op = teclado.nextInt();
            teclado.nextLine();

            switch (op) {
                case 1:
                    System.out.println("\nDigite o cpf do cliente: ");
                    String cpf = teclado.nextLine();
                    if (cpf.isBlank()) {
                        System.out.println("CPF inválido.");
                    } else {
                        if (unidadeAtual.getClientes().isEmpty()) {
                            System.out.println("Nenhum cliente cadastrado nesta unidade.");
                        } else {
                            try {
                                Cliente buscado = Util.buscarCliente(unidadeAtual, cpf);
                                System.out.println("--- Cliente Encontrado ---");
                                System.out.println(buscado);
                            } catch (ClienteInexistenteException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    break;
                case 2:
                    adicionarCliente(unidadeAtual, teclado);
                    break;
                case 3:
                    try {
                        removerCliente(unidadeAtual, teclado);
                        System.out.println("Cliente removido com sucesso.");
                    } catch (ClienteInexistenteException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("\n--- Quadro de Clientes ---");
                    if (unidadeAtual.getClientes().isEmpty()) {
                        System.out.println("Nenhum cliente cadastrado.");
                    } else {
                        for (Cliente c : unidadeAtual.getClientes()) {
                            System.out.println(c);
                        }
                    }
                    break;
                case 5:
                    System.out.println("Retornando ao menu da unidade...");
                    continuar = false;
                    MenuPrincipal.opcoesAcessar();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }
}
