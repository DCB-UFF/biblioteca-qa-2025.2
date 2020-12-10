package biblioteca.testes;
import biblioteca.arquivo.Escritor;
import biblioteca.arquivo.Leitor;
import biblioteca.arquivo.Removedor;
import biblioteca.biblioteca.*;
import biblioteca.pessoas.*;
import java.util.ArrayList;

/* @author luam */

public class TesteUnidade {
    
    public static void main(String[] args){
        /*
        // Leitura
        Sistema sistema = Leitor.leitorUnidades();
        imprimir(sistema.getUnidades());

        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        
        // Escirta
        Unidade nova = new Unidade ("Cabo frio", "Rua Bento Riberio 889", "Centro", "22258-777", "Cabo Frio", "Rio de Janeiro");
        Escritor.escreverUnidade(sistema, nova);
       
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        
        //Remoção
        Removedor.removerUnidade(sistema, 2);
        */
    }
    
    public static void imprimir(ArrayList a){
        for (Object i: a){
           System.out.println(i);
        }
    }
}
