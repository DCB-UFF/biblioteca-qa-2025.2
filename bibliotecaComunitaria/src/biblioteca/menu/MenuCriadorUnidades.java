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
        
        
        
        System.out.println("\nDigite o diretório dos arquivos fonte da nova unidade:");
        String caminho = teclado.nextLine();
       
        
        
        
        File estanteFonte = new File(caminho + "\\estantes.csv");
        File estanteDest = new File(aux.getPath() + "\\estantes.csv");
        Criador.copiarArquivo(estanteFonte, estanteDest);
        
        File autoresFonte = new File(caminho + "\\autores.csv");
        File autoresDest = new File(aux.getPath() + "\\autores.csv");
        Criador.copiarArquivo(autoresFonte, autoresDest);
        
        File livrosFonte = new File(caminho + "\\livros.csv");
        File livrosDest = new File(aux.getPath() + "\\livros.csv");
        Criador.copiarArquivo(livrosFonte, livrosDest);
        
        File emprestimosFonte = new File(caminho + "\\emprestimos.csv");
        File emprestimosDest = new File(aux.getPath() + "\\emprestimos.csv");
        Criador.copiarArquivo(emprestimosFonte, emprestimosDest);
        
        File clientesFonte = new File(caminho + "\\clientes.csv");
        File clientesDest = new File(aux.getPath() + "\\clientes.csv");
        Criador.copiarArquivo(clientesFonte, clientesDest);
        
        File funcionariosFonte = new File(caminho + "\\funcionarios.csv");
        File funcionariosDest = new File(aux.getPath() + "\\funcionarios.csv");
        Criador.copiarArquivo(funcionariosFonte, funcionariosDest);
        
        System.out.printf("\nUnidade %s carregada! ", nome);
    }
}
