package biblioteca.pessoas;

import biblioteca.biblioteca.Endereco;

/* @author victoria */

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

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
    
    @Override
    public String toString() {
        return ("Nome: " + this.getNome() + " - " + "CPF: " + this.CPF + " - " + "Nascimento: " + this.getNascimento() + " - " + "Telefone: " + this.getTelefone() + " - " + "Cargo: " + this.getCargo() + " - " + "Endereço -> " + this.getEnd());
    }

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
