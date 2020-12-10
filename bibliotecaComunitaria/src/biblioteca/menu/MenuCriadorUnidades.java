package biblioteca.menu;

import biblioteca.arquivo.Criador;
import biblioteca.arquivo.Leitor;
import biblioteca.biblioteca.Sistema;
import biblioteca.biblioteca.Unidade;
import java.io.File;
import java.util.Scanner;

/* @author Luam
 */
public class MenuCriadorUnidades {
    public static void gerar(Sistema sistema, Scanner teclado){
        teclado.nextLine();
        System.out.println("\nDigite o nome da unidade: ");
        String nome = teclado.nextLine();
        System.out.println("\nDigite a rua: ");
        String rua = teclado.nextLine();
        System.out.println("\nDigite o bairro: ");
        String bairro = teclado.nextLine();
        System.out.println("\nDigite o cep: ");
        String cep = teclado.nextLine();
        System.out.println("\nDigite a cidade: ");
        String cidade = teclado.nextLine();
        System.out.println("\nDigite o estado: ");
        String estado = teclado.nextLine();
        
        
        sistema.criarUnidade(nome, rua, bairro, cep, cidade, estado);
        sistema = Leitor.leitorUnidades();
        
        System.out.printf("\nUnidade %s criada!\n\n", nome);
        
        Unidade aux = sistema.buscarUnidade(nome);
        Menu.imprimir(sistema.getUnidades());
        
        
        
        System.out.println("\nDigite o nome do arquivo de estantes");
        String estantes = teclado.nextLine();
        System.out.println("\nDigite o nome do arquivo de autores");
        String autores = teclado.nextLine();
        System.out.println("\nDigite o nome do arquivo de livros");
        String livros = teclado.nextLine();
        System.out.println("\nDigite o nome do arquivo de emprestimos");
        String emprestimos = teclado.nextLine();
        System.out.println("\nDigite o nome do arquivo de clientes");
        String clientes = teclado.nextLine();
        System.out.println("\nDigite o nome do arquivo de funcionarios");
        String funcionarios = teclado.nextLine();
        
        
        
        
        File estanteFonte = new File(estantes);
        File estanteDest = new File("src\\unidades\\un"+aux.getPath() +"\\estantes.csv");
        Criador.copiarArquivo(estanteFonte, estanteDest);
        
        File autoresFonte = new File(autores);
        File autoresDest = new File("src\\unidades\\un"+aux.getPath() +"\\autores.csv");
        Criador.copiarArquivo(autoresFonte, autoresDest);
        
        File livrosFonte = new File(livros);
        File livrosDest = new File("src\\unidades\\un"+aux.getPath() +"\\livros.csv");
        Criador.copiarArquivo(livrosFonte, livrosDest);
        
        File emprestimosFonte = new File(emprestimos);
        File emprestimosDest = new File("src\\unidades\\un"+aux.getPath() +"\\emprestimos.csv");
        Criador.copiarArquivo(emprestimosFonte, emprestimosDest);
        
        File clientesFonte = new File(clientes);
        File clientesDest = new File("src\\unidades\\un"+aux.getPath() +"\\clientes.csv");
        Criador.copiarArquivo(clientesFonte, clientesDest);
        
        File funcionariosFonte = new File(funcionarios);
        File funcionariosDest = new File("src\\unidades\\un"+aux.getPath() +"\\funcionarios.csv");
        Criador.copiarArquivo(funcionariosFonte, funcionariosDest);
        
        System.out.printf("\nUnidade %s carregada! ", nome);
    }
}
