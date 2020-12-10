package biblioteca.pessoas;

/* @author victoria */
public interface Cadastravel {
    
    public void cadastro(String nome, String nascimento, String telefone);
    public void cadastro(String nome);
    public void modificar(String nome, String nascimento, String telefone);
    public void excluir();
    
}
