package biblioteca.menu;

import biblioteca.biblioteca.*;
import biblioteca.excecoes.ClienteInexistenteException;
import biblioteca.pessoas.Cliente;
import java.util.Scanner;
import java.util.logging.Logger;

public class MenuCliente {

    private static final Logger LOGGER = Logger.getLogger(MenuCliente.class.getName());

    public static void opcoesAcessarAdminCliente() {
        LOGGER.info("1 - Buscar cliente");
        LOGGER.info("2 - Adicionar cliente");
        LOGGER.info("3 - Remover cliente");
        LOGGER.info("4 - Imprimir quadro de clientes");
        LOGGER.info("5 - Voltar ao menu da unidade\n");
    }

    public static void adicionarCliente(Unidade aux, Scanner tecla) {
        LOGGER.info("\n--- Adicionar Novo Cliente ---");
        LOGGER.info("Digite o nome do cliente: ");
        String nome = tecla.nextLine();
        LOGGER.info("Digite o cpf do cliente: ");
        String cpf = tecla.nextLine();
        LOGGER.info("Digite o nascimento do cliente (dd/mm/aaaa): ");
        String data = tecla.nextLine();
        LOGGER.info("Digite o telefone do cliente: ");
        String tele = tecla.nextLine();
        LOGGER.info("Digite a rua do cliente: ");
        String rua = tecla.nextLine();
        LOGGER.info("Digite o bairro do cliente: ");
        String bairro = tecla.nextLine();
        LOGGER.info("Digite o cep do cliente: ");
        String cep = tecla.nextLine();
        LOGGER.info("Digite a cidade do cliente: ");
        String cidade = tecla.nextLine();
        LOGGER.info("Digite o estado do cliente (UF): ");
        String estado = tecla.nextLine();

        Cliente cliente = new Cliente(nome, cpf, data, tele, rua, bairro, cep, cidade, estado);
        aux.getClientes().add(cliente);

        Cliente.escreverCliente(cliente, aux.getPath());
        LOGGER.info("Cliente adicionado com sucesso!");
    }

    public static void removerCliente(Unidade aux, Scanner tecla) throws ClienteInexistenteException {
        LOGGER.info("\nDigite o cpf do cliente a ser removido: ");
        String cpf = tecla.nextLine();

        if (cpf.isBlank()) {
            LOGGER.info("CPF inválido.");
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
                    LOGGER.info("\nDigite o cpf do cliente: ");
                    String cpf = teclado.nextLine();
                    if (cpf.isBlank()) {
                        LOGGER.info("CPF inválido.");
                    } else {
                        if (unidadeAtual.getClientes().isEmpty()) {
                            LOGGER.info("Nenhum cliente cadastrado nesta unidade.");
                        } else {
                            try {
                                Cliente buscado = Util.buscarCliente(unidadeAtual, cpf);
                                LOGGER.info("--- Cliente Encontrado ---");
                                LOGGER.info(buscado);
                            } catch (ClienteInexistenteException e) {
                                LOGGER.warning(e.getMessage());
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
                        LOGGER.info("Cliente removido com sucesso.");
                    } catch (ClienteInexistenteException e) {
                        LOGGER.warning(e.getMessage());
                    }
                    break;
                case 4:
                    LOGGER.info("\n--- Quadro de Clientes ---");
                    if (unidadeAtual.getClientes().isEmpty()) {
                        LOGGER.info("Nenhum cliente cadastrado.");
                    } else {
                        for (Cliente c : unidadeAtual.getClientes()) {
                            LOGGER.info(c);
                        }
                    }
                    break;
                case 5:
                    LOGGER.info("Retornando ao menu da unidade...");
                    continuar = false;
                    MenuPrincipal.opcoesAcessar();
                    break;
                default:
                    LOGGER.info("Opção inválida.");
                    break;
            }
        }
    }
}
