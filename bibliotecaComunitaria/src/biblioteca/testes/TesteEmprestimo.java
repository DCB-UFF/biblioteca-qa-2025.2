package biblioteca.testes;
import biblioteca.arquivo.Escritor;
import biblioteca.arquivo.Leitor;
import biblioteca.arquivo.Removedor;
import biblioteca.biblioteca.*;
import biblioteca.livros.Emprestimo;
import java.util.ArrayList;

/* @author luam */

public class TesteEmprestimo {
    
    public static void main(String[] args){
        
        // Leitura
        ArrayList<Emprestimo> emprestimos = Leitor.leitorEmprestimos(("src\\unidades\\un1\\"));
        imprimir(emprestimos);

        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        
        // Escirta
        Emprestimo novo = new Emprestimo("123456789-10","23","12/12/12","19/12/12");
        Escritor.escreverEmprestimo(novo, "src\\unidades\\un1\\");
       
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        
        //Remoção
        Emprestimo novo2 = new Emprestimo("150999497-88","1","09/12/20","17/12/20");
        Removedor.removerEmprestimo(novo2,"src\\unidades\\un1\\");
    
    }
    
    public static void imprimir(ArrayList a){
        for (Object i: a){
           System.out.println(i.toString());
        }
    }
}
