package biblioteca.biblioteca;

import biblioteca.arquivo.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.util.ArrayList;

/* @author Luam */

public class Main {
    public static void main(String[] args){
        Sistema sistema = new Sistema();
        lerUnidade("1");
       
    }
    
    
    
    public static void lerUnidade(String num){
         //Cria unidade
        Unidade unNiteroi = new Unidade("src\\unidades\\un"+ num+"\\","Niteroi", "Rua Marechal Floriano 43", "Inga", "23441-001", "Niteroi", "Rio de Janeiro");
        
        //Lê o acervo
        Acervo acervoNiteroi = Leitor.leitorEstantes(unNiteroi.getPath());
        ArrayList<Autor> autores = Leitor.leitorAutores(unNiteroi.getPath());
        acervoNiteroi.setAutores(autores);
        Leitor.leitorLivros(acervoNiteroi,unNiteroi.getPath());
        unNiteroi.setAcervo(acervoNiteroi);
        //unNiteroi.getAcervo().imprimirAcervo(unNiteroi.getNome());
       
        //Lê os clientes
        ArrayList<Cliente> clientesNiteroi = Leitor.leitorClientes(unNiteroi.getPath());
        unNiteroi.setClientes(clientesNiteroi);
        //imprimir(unNiteroi.getClientes());
        
        //Lê os funcionarios
        ArrayList<Funcionario> funcionariosNiteroi = Leitor.leitorFuncionarios(unNiteroi.getPath());
        unNiteroi.setFuncionarios(funcionariosNiteroi);
        //imprimir(unNiteroi.getFuncionarios());
    
    }
    
    
    
    public static void imprimir(ArrayList a){
        for (Object i: a){
           System.out.println(i);
        }
    }
}
