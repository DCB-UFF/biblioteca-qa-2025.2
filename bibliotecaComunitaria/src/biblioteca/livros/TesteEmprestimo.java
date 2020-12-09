package biblioteca.livros;

import biblioteca.arquivo.Leitor;
import biblioteca.biblioteca.Sistema;
import biblioteca.biblioteca.Unidade;
import biblioteca.pessoas.Autor;
import biblioteca.pessoas.Cliente;
import java.util.ArrayList;

/* @author Luam */

public class TesteEmprestimo {
    public static void main(String[] args){
        
        Unidade unidade = new Unidade ("São Gonçalo", "Rua Bento Riberio 889", "Centro", "22258-777", "Cabo Frio", "Rio de Janeiro");
        
        
        ArrayList<Autor> autores = Leitor.leitorAutores("src\\unidades\\un1\\");
        Acervo acervo = Leitor.leitorEstantes("src\\unidades\\un1\\");
        Leitor.leitorLivros(acervo,"src\\unidades\\un1\\");
        
        unidade.setAcervo(acervo);
        
        acervo.imprimirAcervo("Teste");
        
        Cliente maria = new Cliente("Maria", "123456789-10", "12/12/2000", "99999-9999", "Rua 1", "Campo Grande", "22222-222", "Rio de Janeiro", "RJ");
        
        
        acervo.emprestarLivro(unidade, maria, "A casa dos Espíritos");
        
        acervo.imprimirAcervo("São Gonçalo");
    
    }
}
