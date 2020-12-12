package biblioteca.biblioteca;

import biblioteca.excecoes.*;
import biblioteca.livros.*;
import biblioteca.pessoas.Cliente;
import java.util.ArrayList;

/* @author Luam */

public class Util {
    public static void imprimir(ArrayList a) {
        for (Object i : a) {
            System.out.println(i);
        }
    }   
    
    public static void buscarCliente(Unidade unidadeatual, String cpf) throws ClienteInexistenteException{
        for(Cliente c : unidadeatual.getClientes()){
            if(c.getCPF().equals(cpf)){
                System.out.println(c);
            }
        }
        throw new ClienteInexistenteException();
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
    
}
