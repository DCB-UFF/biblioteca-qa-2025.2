package biblioteca.pessoas;
import java.util.ArrayList;
import biblioteca.livros.Livro;

/**
 *
 * @author victoria
 */
public class Autor extends Pessoa{
    
    protected String biografia;
    protected String pais;
    protected ArrayList<Livro> livrosAutor = new ArrayList<>();

    public Autor() {    
    }
    
    
    
    public void addLivro(Livro livro){
        this.livrosAutor.add(livro);
    }
    
    public Autor (String nome){
        super.cadastro(nome);
    }
  
    
    @Override
    public void cadastro(String nome, String biografia, String pais){
        
        super.cadastro(nome);
        this.biografia = biografia;
        this.pais = pais;
    
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public void excluir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
