package biblioteca.pessoas;

import biblioteca.biblioteca.Endereco;
import biblioteca.livros.Livro;
import java.util.ArrayList;

/* @author victoria */

public class Cliente extends Pessoa{
    
    ArrayList<Livro> historico = new ArrayList<>();
    private Endereco end;

    public Cliente(String nome, String nascimento, String telefone, String rua, String bairro, String cep, String cidade, String estado){
        
        super.cadastro(nome, nascimento, telefone);
        this.end = new Endereco(rua, bairro, cep, cidade, estado);
    
    }

    @Override
    public void excluir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return ("Nome: " + this.getNome() + " - " + "Nascimento: " + this.getNascimento() + " - " + "Telefone: " + this.getTelefone() + " - " + "Endereço -> " + this.getEnd());
    }
    
    protected void cobrarDevolucao(){
    
        // se o cliente não tiver devolvido um livro não deixar pegar outro
        
    }

    public Endereco getEnd() {
        return end;
    }
    
}
