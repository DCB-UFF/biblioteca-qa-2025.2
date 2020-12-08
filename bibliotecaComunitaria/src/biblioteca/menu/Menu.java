package biblioteca.menu;

import biblioteca.biblioteca.Unidade;
import java.util.Scanner;

/*@author victoria */

public class Menu {
    /*
    
        escolher a unidade
            escolher o que adicionar (funcionario, livros...)
        adicionar unidade nova
            adicionar o resto

    */
    
    public static void chamada(){
        
        System.out.println("Sejam bem-vindos a Biblioteca Comunitária");
        Inicial.principal();
        Scanner teclado = new Scanner(System.in);
        Scanner tecla = new Scanner(System.in);
        int op = teclado.nextInt();
        while(op != 3){
            if(op == 1){
                //pedir o nome da biblioteca e o endereço
               System.out.println("\nDigite o caminho do arquivo: "); 
            }
            else if(op == 2){
                Inicial.escolher();
                op = teclado.nextInt();
                String uni = tecla.nextLine();
                Unidade aux = Inicial.buscarUnidade(uni, Main.sistema);
                while(op != 5){
                    switch (op) {
                        case 1:
                            Inicial.adicionarLivro(aux);
                            break;
                        case 2:
                            Inicial.adicionarFuncionario(aux);
                            break;
                        case 3:
                            Inicial.adicionarAutor(aux);
                            break;
                        case 4:
                            Inicial.adicionarCliente(aux);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        
    }
}
