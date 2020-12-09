package biblioteca.menu;

import biblioteca.biblioteca.Unidade;
import java.util.Scanner;

/*@author victoria */

public class Menu {

    public static void chamada(){
        
        System.out.println("Sejam bem-vindos a Biblioteca Comunitária");
        Inicial.principal();
        Scanner teclado = new Scanner(System.in);
        int op = teclado.nextInt();
        while(op != 3){
            if(op == 1){
                Inicial.imprimir(Main.sistema.unidades);
                System.out.println("Digite o nome da unidade: ");
                Scanner tecla = new Scanner(System.in);
                String uni = tecla.nextLine();
                Unidade aux = Inicial.buscarUnidade(uni, Main.sistema);
                Inicial.escolher();
                op = teclado.nextInt();
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
            else if(op == 2){
                Inicial.opcoesAcessar();
            }
        }
        
    }
}
