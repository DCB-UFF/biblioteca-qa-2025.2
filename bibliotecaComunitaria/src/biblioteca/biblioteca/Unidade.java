package biblioteca.biblioteca;
import biblioteca.livros.Acervo;
import biblioteca.pessoas.Cliente;
import java.util.ArrayList;

/* @author victoria */

public class Unidade{
    
    protected ArrayList <Cliente> clientes = new ArrayList<>();
    protected Acervo acervo;
    protected String nome;
    public Endereco end;
    
    public Unidade(String nome, String rua, String bairro, String cep, String cidade, String estado){
        this.nome = nome;
        this.end = new Endereco(rua, bairro, cep, cidade, estado);
    }

}
