
package biblioteca.menu;

import biblioteca.arquivo.*;
import biblioteca.biblioteca.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.util.ArrayList;
import java.util.Scanner;

/* @author victoria */
public class Inicial {
    
    public static void principal(){
        
        System.out.println("\n1 - Escolher unidade");
        System.out.println("2 - Adicionar nova unidade");
        System.out.println("3 - Sair\n");
        
    }
    
    public static void opcoesAcessar(){
        System.out.println("\n1 - Acesso");
        System.out.println("2 - Emprestimo");
        System.out.println("3 - Administração\n");
    }
    
    public static void escolher(){

        System.out.println("\nEscolha o que deseja adicionar: ");
        System.out.println("1 - Livro");
        System.out.println("2 - Funcionário");
        System.out.println("3 - Autor");
        System.out.println("4 - Cliente");
        System.out.println("5 - Sair\n");
        
    }
    
    public static Unidade buscarUnidade(String nome, Sistema sistema){

        for(Unidade u: sistema.unidades){
            if(u.getNome().equals(nome)){
                System.out.println("oi\n");
                return u;
            }
        }
            //tratamento de exceção 
        return null;
    }
    
    public static void adicionarLivro(Unidade aux){
        
        Scanner teclado = new Scanner(System.in);
        Scanner tecla = new Scanner(System.in);
        
        int id = aux.getAcervo().idsLivros++;
        System.out.println("\nDigite o nome do autor: ");
        String nome = tecla.nextLine();
        System.out.println("\nDigite o país do autor: ");
        String pais = tecla.nextLine();
        Autor autor = new Autor(nome, pais);
        System.out.println("\nDigite o título do livro: ");
        String titulo = tecla.nextLine();
        System.out.println("\nDigite o isbn do livro: ");
        String isbn = tecla.nextLine();
        System.out.println("\nDigite a quantidade de páginas do livro: ");
        int pag = teclado.nextInt();
        System.out.println("\nDigite o gênero do livro: ");
        String gene = tecla.nextLine();
        System.out.println("\nDigite a editora do livro: ");
        String edit = tecla.nextLine();
        Livro novo = new Livro(id, autor, titulo, isbn, pag, gene, edit, false);               
        aux.getAcervo().addLivro(novo);
        Escritor.escreverLivro(novo, aux.getPath());
        
    }
    
    public static void adicionarFuncionario(Unidade aux){
        
        Scanner teclado = new Scanner(System.in);
        Scanner tecla = new Scanner(System.in);
        
        System.out.println("\nDigite o nome do funcionário: ");
        String nome = tecla.nextLine();
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
                        
        Funcionario fun = new Funcionario(nome, data, tele, sala, cargo, rua, bairro, cep, cid, est);
        aux.getFuncionarios().add(fun);
        Escritor.escreverFuncionario(fun, aux.getPath());
        
    }
    
    public static void adicionarAutor(Unidade aux){
        
        Scanner tecla = new Scanner(System.in);
        
        System.out.println("\nDigite o nome do autor: ");
        String nome = tecla.nextLine();
        System.out.println("\nDigite o país do autor: ");
        String pais = tecla.nextLine();
                        
        Autor autor = new Autor(nome, pais);
        aux.getAcervo().getAutores().add(autor);
        Escritor.escreverAutor(autor, aux.getPath());
        
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
    
    public static void adicionarUnidade(Sistema sistema){
        
        Scanner tecla = new Scanner(System.in);
        
        System.out.println("\nDigite o nome do cliente: ");
        String nome = tecla.nextLine();
        System.out.println("\nDigite a rua da unidade: ");
        String rua = tecla.nextLine();
        System.out.println("\nDigite o bairro da unidade: ");
        String bairro = tecla.nextLine();
        System.out.println("\nDigite o cep da unidade: ");
        String cep = tecla.nextLine();
        System.out.println("\nDigite a cidade da unidade: ");
        String cid = tecla.nextLine();
        System.out.println("\nDigite o estado da unidade: ");
        String est = tecla.nextLine();
        
        Unidade unidade = new Unidade(String.valueOf(sistema.getContadorUnidades()), nome, rua, bairro, cep, cid, est);
        sistema.add(unidade);
        Escritor.escreverUnidade(sistema, unidade);
    
    }

    public static void imprimir(ArrayList a){
        for (Object i: a){
           System.out.println(i);
        }
    }
    
}
