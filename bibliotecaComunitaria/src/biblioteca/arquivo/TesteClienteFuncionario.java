package biblioteca.arquivo;
import biblioteca.pessoas.Cliente;
import biblioteca.pessoas.Funcionario;
import java.util.ArrayList;

/* @author victoria */

public class TesteClienteFuncionario {
    
    public static void main(String[] args){
        ArrayList<Cliente> clientes = new ArrayList();
        
        clientes = Leitor.leitorClientes();
        imprimir(clientes);

        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        
        ArrayList<Funcionario> funcionarios = new ArrayList();

        funcionarios = Leitor.leitorFuncionarios();
        imprimir(funcionarios);
        
        /*Cliente cliente = new Cliente();
        cliente.cadastro("Maria", "12/12/2000", "99999-9999", "Rua 1", "Campo Grande", "22222-222", "Rio de Janeiro", "RJ");
        Escritor.escreverCliente(cliente);
        clientes = Leitor.leitorClientes();
        LeituraPessoas.imprimir(clientes);*/
        
        /*Funcionario funcionario = new Funcionario();
        funcionario.cadastro("Maria", "12/12/2000", "99999-9999", 4000, "gerente", "Rua 1",
        "Campo Grande", "22222-222", "Rio de Janeiro", "RJ");
        
        Escritor.escreverFuncionario(funcionario);
        funcionarios = Leitor.leitorFuncionarios();
        imprimir(funcionarios);*/
        
        
        /* Testando remoção
        Funcionario funcionario = new Funcionario();
        funcionario.cadastro("Maria", "12/12/2000", "99999-9999", 4000, "gerente", "Rua 1",
        "Campo Grande", "22222-222", "Rio de Janeiro", "RJ");
        
        Removedor.removerFuncionario(funcionario);
        */
        Cliente cliente = new Cliente();
        cliente.cadastro("Maria", "12/12/2000", "99999-9999", "Rua 1", "Campo Grande", "22222-222", "Rio de Janeiro", "RJ");
        Removedor.removerCliente(cliente);
        
        
        
        
        
        
    }
    
    public static void imprimir(ArrayList a){
        for (Object i: a){
           System.out.println(i);
        }
    }
}
