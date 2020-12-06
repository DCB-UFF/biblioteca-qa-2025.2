/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.pessoas;

/**
 *
 * @author victoria
 */
public abstract class Pessoa implements Cadastravel{
    
    protected String nome;
    protected String nascimento;
    protected String telefone;
    
    @Override
    public void cadastro(String nome, String nascimento, String telefone){
    
        this.nome = nome;
        this.nascimento = nascimento;
        this.telefone = telefone;
    
    };
    
    @Override
     public void cadastro(String nome){
    
        this.nome = nome;
    
    };
    
    @Override
    public void modificar(String nome, String nascimento, String telefone){
    
        this.nome = nome;
        this.nascimento = nascimento;
        this.telefone = telefone;
        
    };

    @Override
    public abstract void excluir();

    public String getNome() {
        return nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public String getTelefone() {
        return telefone;
    }
    
}
