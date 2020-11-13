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
public class Funcionario extends Pessoa{
    
    protected float salario;
    protected String cargo;
    protected boolean ferias;

    protected void cadastro(String nome, String nascimento, String telefone, float salario, String cargo){
        
        super.cadastro(nome, nascimento, telefone);
        this.salario = salario;
        this.cargo = cargo;
    
    }
    
    @Override
    protected void excluir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    protected void estaFerias(){
        if(this.ferias){
            System.out.println("Funcionário " + this.nome + " está de férias\n");
        }else{
            System.out.println("Funcionário " + this.nome + " não está de férias\n");
        }
    }
    
    protected void ajusteSalario(){}
}
