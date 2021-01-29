package biblioteca.biblioteca;
import biblioteca.livros.Acervo;
import biblioteca.pessoas.Cliente;
import biblioteca.pessoas.Funcionario;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/* @author victoria */

public class Unidade{
    private String path;
    private String nome;
    private Endereco end;
    private Acervo acervo;
    private ArrayList <Cliente> clientes = new ArrayList<>();
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();
    
    public Unidade(String path, String nome, String rua, String bairro, String cep, String cidade, String estado){
        this.path = "src\\unidades\\un"+ path +"\\";
        this.nome = nome;
        this.end = new Endereco(rua, bairro, cep, cidade, estado);
    }
    
    @Override
     public String toString() {
         return ("Unidade "+ this.nome + "\nRua: " + end.getRua() + " - " + "Bairro: " + end.getBairro() +
                 " - " + "CEP: " + end.getCep() + " - " + "Cidade: " + end.getCidade() + " - " + "Estado: " + end.getEstado());
     }

    public Endereco getEnd() {
        return end;
    }

    public void setEnd(Endereco end) {
        this.end = end;
    }
     
     
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
     
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    public Acervo getAcervo() {
        return acervo;
    }
    public void setAcervo(Acervo acervo) {
        this.acervo = acervo;
    }
    
    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }
    public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
    
    public static void criarPastaUnidade(String num) {
        File file = new File("src\\unidades\\un"+num); file.mkdir();
        File aut = new File("src\\unidades\\un"+num+"\\autores.csv");
        File cli = new File("src\\unidades\\un"+num+"\\clientes.csv");
        File emp = new File("src\\unidades\\un"+num+"\\emprestimos.csv");
        File est = new File("src\\unidades\\un"+num+"\\estantes.csv");
        File fun = new File("src\\unidades\\un"+num+"\\funcionarios.csv");
        File liv= new File("src\\unidades\\un"+num+"\\livros.csv");
        
        try {
            aut.createNewFile();
            cli.createNewFile();
            emp.createNewFile();
            est.createNewFile();
            fun.createNewFile();
            liv.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Unidade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
