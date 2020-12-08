package biblioteca.menu;

import biblioteca.arquivo.*;
import biblioteca.biblioteca.Sistema;
import biblioteca.biblioteca.Unidade;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.util.ArrayList;

/* @author Luam */

public class Main {
    
    public static Sistema sistema;
    
    public static void main(String[] args){
        sistema = Leitor.leitorUnidades();
        for(Unidade u : sistema.unidades){
            lerUnidade(u.getPath());
        }
        Menu.chamada();
    }
    
    // Tenho que ler numero, nome e endereço de cada unidade e depois gerar a unidade
    
    public static void lerUnidade(String num){
         //Cria unidade
        Unidade un = new Unidade("src\\unidades\\un"+ num+"\\","Niteroi", "Rua Marechal Floriano 43", "Inga", "23441-001", "Niteroi", "Rio de Janeiro");
        
        //Lê o acervo
        Acervo acervoUn = Leitor.leitorEstantes(un.getPath());
        ArrayList<Autor> autores = Leitor.leitorAutores(un.getPath());
        acervoUn.setAutores(autores);
        Leitor.leitorLivros(acervoUn,un.getPath());
        un.setAcervo(acervoUn);
        //unNiteroi.getAcervo().imprimirAcervo(un.getNome());
       
        //Lê os clientes
        ArrayList<Cliente> clientesNiteroi = Leitor.leitorClientes(un.getPath());
        un.setClientes(clientesNiteroi);
        //imprimir(un.getClientes());
        
        //Lê os funcionarios
        ArrayList<Funcionario> funcionariosNiteroi = Leitor.leitorFuncionarios(un.getPath());
        un.setFuncionarios(funcionariosNiteroi);
        //imprimir(un.getFuncionarios());
    
    }
    
    public static void imprimir(ArrayList a){
        for (Object i: a){
           System.out.println(i);
        }
    }
}
