package biblioteca.biblioteca;

import biblioteca.arquivo.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.util.ArrayList;

/* @author Luam */

public class Main {
    public static void main(String[] args){
        Sistema sistema = new Sistema();
        
        //Cria unidade
        Unidade unNiteroi = new Unidade("Niteroi", "Rua Marechal Floriano 43", "Inga", "23441-001", "Niteroi", "Rio de Janeiro");
        
        //Lê o acervo
        Acervo acervoNiteroi = Leitor.leitorEstantes();
        ArrayList<Autor> autores = Leitor.leitorAutores();
        acervoNiteroi.setAutores(autores);
        Leitor.leitorLivros(acervoNiteroi);
        unNiteroi.setAcervo(acervoNiteroi);
       
        //Lê os clientes
        ArrayList<Cliente> clientesNiteroi = Leitor.leitorClientes();
        unNiteroi.setClientes(clientesNiteroi);
        
        //Lê os funcionarios
        ArrayList<Funcionario> funcionariosNiteroi = Leitor.leitorFuncionarios();
        unNiteroi.setFuncionarios(funcionariosNiteroi);
        
        
        
    }
    
    public static void imprimir(ArrayList a){
        for (Object i: a){
           System.out.println(i);
        }
    }
}
