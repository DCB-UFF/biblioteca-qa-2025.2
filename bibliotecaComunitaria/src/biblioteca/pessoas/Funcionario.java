/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.pessoas;

import biblioteca.biblioteca.Endereco;

/**
 *
 * @author victoria
 */
public class Funcionario extends Pessoa{
    
    private String CPF;
    private float salario;
    private String cargo;
    private Endereco end;

    public Funcionario(String nome, String CPF,String nascimento, String telefone, float salario, String cargo, String rua, String bairro, String cep, String cidade, String estado){
        
        super.cadastro(nome, nascimento, telefone);
        this.CPF = CPF;
        this.salario = salario;
        this.cargo = cargo;
        this.end = new Endereco(rua, bairro, cep, cidade, estado);
    
    }
    
    @Override
    public void excluir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return ("Nome: " + this.getNome() + " - " + "Nascimento: " + this.getNascimento() + " - " + "Telefone: " + this.getTelefone() + " - " + "Cargo: " + this.getCargo() + " - " + "Endereço -> " + this.getEnd());
    }
    
    protected void ajusteSalario(){}

    public float getSalario() {
        return salario;
    }

    public String getCargo() {
        return cargo;
    }

    public Endereco getEnd() {
        return end;
    }
}
