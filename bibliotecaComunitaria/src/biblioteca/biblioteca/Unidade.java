/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.biblioteca;

/**
 *
 * @author victoria
 */
public class Unidade extends Endereco{
    
    protected String nome;
    
    public void Endereco(String nome, String rua, String bairro, String cep, String cidade, String estado){
        super.Endereco(rua, bairro, cep, cidade, estado);
        this.nome = nome;
    }
    
}
