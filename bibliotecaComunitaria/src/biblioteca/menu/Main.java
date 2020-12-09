package biblioteca.menu;

import biblioteca.arquivo.Leitor;
import biblioteca.biblioteca.*;
import java.util.ArrayList;

/* @author Luam */
public class Main {
    public static Sistema sistema;
    
    public static void main(String[] args){
        sistema = Leitor.leitorUnidades();
        sistema.carregarUnidades();
        //imprimir(sistema.getUnidades());
        /*for (Unidade u: sistema.getUnidades()){
            u.getAcervo().imprimirAcervo(u.getNome());
        }*/
        Menu.chamada();
        System.out.println("------------------------------------------");
        
        /*sistema.criarUnidade("Ouro Preto", "Rua da Mina 98", "Centro Histórico",
                "24728-55", "Ouro Preto", "Minas Gerais");
        sistema = Leitor.leitorUnidades();
        imprimir(sistema.getUnidades());*/
    }
    
    public static void imprimir(ArrayList a){
        for (Object i: a){
           System.out.println(i);
        }
    }
}
