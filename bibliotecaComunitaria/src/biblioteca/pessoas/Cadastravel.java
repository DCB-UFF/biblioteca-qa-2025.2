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
public interface Cadastravel {
    
    public void cadastro(String nome, String nascimento, String telefone);
    public void modificar(String nome, String nascimento, String telefone);
    public void excluir();
    
}
