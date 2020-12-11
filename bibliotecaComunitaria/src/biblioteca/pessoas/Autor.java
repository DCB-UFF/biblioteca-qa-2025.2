package biblioteca.pessoas;
import java.util.ArrayList;
import biblioteca.livros.Livro;

/* @author victoria */

public class Autor extends Pessoa{
    protected String pais;
    protected ArrayList<Livro> livrosAutor = new ArrayList<>();

    public Autor() {    
    }

    public Autor(String nome, String pais) {
        super.cadastro(nome);
        this.pais = pais;
    }

    public ArrayList<Livro> getLivrosAutor() {
        return livrosAutor;
    }

    public void setLivrosAutor(ArrayList<Livro> livrosAutor) {
        this.livrosAutor = livrosAutor;
    }
    
    
    public void addLivro(Livro livro){
        this.livrosAutor.add(livro);
    }
    
    public Autor (String nome){
        super.cadastro(nome);
    }
    
    public void cadastro(String nome, String pais){
        super.cadastro(nome);
        this.pais = pais;
    
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
}
