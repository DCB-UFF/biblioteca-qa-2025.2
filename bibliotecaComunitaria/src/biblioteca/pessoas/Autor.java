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
public class Autor extends Pessoa{
    
    protected String biografia;
    protected String pais;
    
    @Override
    public void cadastro(String nome, String biografia, String pais){
        
        super.cadastro(nome);
        this.biografia = biografia;
        this.pais = pais;
    
    }

    @Override
    public void excluir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
