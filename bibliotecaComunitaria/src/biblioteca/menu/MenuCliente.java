package biblioteca.menu;

import biblioteca.arquivo.Escritor;
import biblioteca.arquivo.Removedor;
import biblioteca.biblioteca.*;
import biblioteca.excecoes.ClienteInexistenteException;
import biblioteca.pessoas.Cliente;
import static java.lang.System.exit;
import java.util.Scanner;

/* @author victoria */
public class MenuCliente {
     public static void opcoesAcessarAdminCliente(){
        
        System.out.println("\n1 - Buscar cliente");
        System.out.println("2 - Adicionar cliente");
        System.out.println("3 - Remover cliente");
        System.out.println("4 - imprimir quadro de clientes\n");
        
    }
    
    public static void adicionarCliente(Unidade aux){
        
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
        Escritor.escreverCliente(cliente, aux.getPath());
        
    } 
    
    public static void removerCliente(Unidade unidadeAtual) throws ClienteInexistenteException{
        Scanner tecla = new Scanner(System.in);
        System.out.println("\nDigite o cpf do cliente: ");
        String cpf = tecla.nextLine();
        
        Cliente buscado = Util.buscarCliente(unidadeAtual, cpf);
        unidadeAtual.getClientes().remove(buscado);
        Removedor.removerCliente(buscado, unidadeAtual.getPath());
        System.out.println("O cliente de cpf "+cpf+"foi removido!");

    }
    
    public static void iniciar(Unidade unidadeAtual, Scanner teclado) throws ClienteInexistenteException{
        opcoesAcessarAdminCliente();
        int op = teclado.nextInt();
        teclado.nextLine();
        while(op != 5){
            switch(op){
                case 1:
                    System.out.println("Digite o cpf do cliente: ");
                    String cpf = teclado.nextLine();
                    Cliente buscado = Util.buscarCliente(unidadeAtual, cpf);
                    System.out.println(buscado);
                    exit(0);
                    break;
                case 2:
                    adicionarCliente(unidadeAtual);
                    exit(0);
                    break;
                case 3:
                    removerCliente(unidadeAtual);
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
    }
}