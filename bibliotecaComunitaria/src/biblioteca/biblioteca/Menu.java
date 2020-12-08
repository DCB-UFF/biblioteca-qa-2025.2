/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.biblioteca;

import java.util.Scanner;

/**
 *
 * @author victoria
 */

public class Menu {
    /*
    
        escolher a unidade
            escolher o que adicionar (funcionario, livros...)
        adicionar unidade nova
            adicionar o resto

    */
    
    public static void principal(){
        
        System.out.println("\n1 - Escolher unidade");
        System.out.println("2 - Adicionar nova unidade");
        System.out.println("3 - Sair\n");
        
    }
    
    public static void escolher(){
        
        /* imprimir unidades disponíveis */
        System.out.println("\nDigite o nome da unidade: ");
        System.out.println("\nEscolha o que deseja adicionar: ");
        System.out.println("1 - Livro");
        System.out.println("2 - Funcionário");
        System.out.println("3 - Autor");
        System.out.println("4 - Cliente");
        System.out.println("5 - Sair\n");
        
    }
    
    public static void nova(){
        
        System.out.println("\nDigite o caminho do arquivo: ");
        
    }
    
    public static Unidade buscarUnidade(String nome){
        /*
            for(Unidade u: biblioteca.unidades){
                if(u.getNome().equals(nome){
                    return u;
                }
            }
            tratamento de ex~ceção 
        */
        return null;
    }
    
    public static void chamada(){
        
        System.out.println("Sejam bem-vindos a Biblioteca Comunitária");
        principal();
        Scanner teclado = new Scanner(System.in);
        int op = teclado.nextInt();
        while(op != 3){
            if(op == 1){
                //pedir o nome da biblioteca e o endereço
               System.out.println("\nDigite o caminho do arquivo: "); 
            }
            else if(op == 2){
                escolher();
                int ope = teclado.nextInt();
                //buscar unidade
                while(ope != 5){
                    if(ope == 1){
                        
                    }
                    else if(ope == 2){

                    }
                    else if(ope == 3){

                    }
                    else if(ope == 4){

                    }
                }
            }
        }
        
    }
}
