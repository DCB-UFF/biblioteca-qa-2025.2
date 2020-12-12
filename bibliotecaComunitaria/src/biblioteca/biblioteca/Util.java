package biblioteca.biblioteca;

import biblioteca.excecoes.*;
import biblioteca.livros.*;
import biblioteca.pessoas.Cliente;
import biblioteca.pessoas.Funcionario;
import java.util.ArrayList;

/* @author victoria */

public class Util {
    public static void imprimir(ArrayList a) {
        for (Object i : a) {
            System.out.println(i);
        }
    }   
    
    public static Cliente buscarCliente(Unidade unidadeatual, String cpf) throws ClienteInexistenteException{
        for(Cliente c : unidadeatual.getClientes()){
            if(c.getCPF().equals(cpf)){
                return c;
            }
        }
        throw new ClienteInexistenteException();
    }
    
    public static Funcionario buscarFuncionario(Unidade unidadeatual, String cpf) throws FuncionarioInexistenteException {
        for(Funcionario f : unidadeatual.getFuncionarios()){
            if(f.getCPF().equals(cpf)){
                return f;
            }
        }
        throw new FuncionarioInexistenteException();
    }
    
    public static Livro buscarLivroTitulo (String titulo, Unidade u) throws LivroNaoExistenteException{
        
        for (Estante e : u.getAcervo().getEstantes()){
            for (Livro l : e.getLivros()){
                if (l.getTitulo().equals(titulo)){
                    return l;
                }
            }
        }
        throw new LivroNaoExistenteException();
    }
    
    public static Unidade buscarUnidade(String nome, Sistema sistema) throws UnidadeInexistenteException{
        
        for(Unidade u: sistema.unidades){
            if(u.getNome().equals(nome)){
                return u;
            }
        } 
        throw new UnidadeInexistenteException();
    }  
    
}
