package biblioteca.pessoas;

import biblioteca.biblioteca.Endereco;
import biblioteca.livros.Livro;
import java.util.ArrayList;

/* @author victoria */

public class Cliente extends Pessoa{
    
    public int livrosPegos;
    private Endereco end;

    public Cliente(String nome, String nascimento, String telefone, String rua, String bairro, String cep, String cidade, String estado){
        
        super.cadastro(nome, nascimento, telefone);
        this.end = new Endereco(rua, bairro, cep, cidade, estado);
    
    }
    
    public void addLivrosPegos(){
        this.livrosPegos++;
    }
    
    public void decrementLivrosPegos(){
        this.livrosPegos--;
    }
    
    public int getLivrosPegos() {
        return livrosPegos;
    }

    public void setLivrosPegos(int livrosPegos) {
        this.livrosPegos = livrosPegos;
    }
    
    
    

    @Override
    public void excluir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return ("Nome: " + this.getNome() + " - " + "Nascimento: " + this.getNascimento() + " - " + "Telefone: " + this.getTelefone() + " - " + "Endereço -> " + this.getEnd());
    }
    

    public Endereco getEnd() {
        return end;
    }
    
}
