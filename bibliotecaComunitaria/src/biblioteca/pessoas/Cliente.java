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
public class Cliente extends Pessoa{
    
    @Override
    public void cadastro(String nome, String nascimento, String telefone){
        
        super.cadastro(nome, nascimento, telefone);
    
    }

    @Override
    public void excluir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    protected void cobrarDevolucao(){
    
        // se o cliente não tiver devolvido um livro não deixar pegar outro
        
    }
    
}
