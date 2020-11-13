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
public abstract class Pessoa {
    
    protected String nome;
    protected String nascimento;
    protected String telefone;
    
    protected void cadastro(String nome, String nascimento, String telefone){
    
        this.nome = nome;
        this.nascimento = nascimento;
        this.telefone = telefone;
    
    };
    protected void modificar(String nome, String nascimento, String telefone){
    
        this.nome = nome;
        this.nascimento = nascimento;
        this.telefone = telefone;
        
    };
    protected abstract void excluir();
    
}
