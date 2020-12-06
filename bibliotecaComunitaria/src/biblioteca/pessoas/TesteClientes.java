/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.pessoas;
import java.util.ArrayList;

/**
 *
 * @author victoria
 */
public class TesteClientes {
    
    public static void main(String[] args){
        ArrayList<Cliente> clientes = new ArrayList();
        
        clientes = LeituraPessoas.leitorClientes();
        LeituraPessoas.imprimir(clientes);

        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        
        ArrayList<Funcionario> funcionarios = new ArrayList();

        funcionarios = LeituraPessoas.leitorFuncionarios();
        LeituraPessoas.imprimir(funcionarios);
    }

}
